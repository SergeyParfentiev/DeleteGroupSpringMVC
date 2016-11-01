package ua.kiev.prog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.service.ContactService;
import ua.kiev.prog.tables.Contact;
import ua.kiev.prog.tables.Group;

@Controller
@RequestMapping("/")
public class MyController {
    static final int DEFAULT_GROUP_ID = -1;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(null));
        return "index";
    }

    @RequestMapping("/contact_add_page")
    public String contactAddPage(Model model) {
        model.addAttribute("groups", contactService.listGroups());
        return "contact_add_page";
    }

    @RequestMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }

    @RequestMapping("/group_delete_page")
    public String groupDeletePage(Model model) {
        model.addAttribute("groups", contactService.listGroups());
        return "group_delete_page";}

    @RequestMapping("/group/{id}")
    public String listGroup(@PathVariable(value = "id") long groupId, Model model) {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;

        model.addAttribute("groups", contactService.listGroups());
//        model.addAttribute("currentGroup", group);
        model.addAttribute("contacts", contactService.listContacts(group));
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.searchContacts(pattern));
        return "index";
    }

    @RequestMapping(value = "/contact/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(@RequestParam(value = "toDelete[]", required = false) long[] toDelete, Model model) {
        if (toDelete != null)
            contactService.deleteContact(toDelete);

        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(null));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/contact/add", method = RequestMethod.POST)
    public String contactAdd(@RequestParam(value = "group") long groupId,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String phone,
                             @RequestParam String email,
                             Model model)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;

        Contact contact = new Contact(group, name, surname, phone, email);
        contactService.addContact(contact);

        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(null));
        return "redirect:/";
    }

    @RequestMapping(value="/group/add", method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name, Model model)
    {
        contactService.addGroup(new Group(name));

        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(null));
        return "index";
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    public String groupDelete(@RequestParam("choose") String choose, @RequestParam(value = "group") long groupId, Model model) {
        String delete = "Delete";
        if(delete.equals(choose)) {
            Group group = contactService.findGroup(groupId);
            contactService.deleteGroup(group);
        }
        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(null));
        return "index";
    }
}
