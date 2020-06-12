package com.example.transport.controller;

import com.example.transport.bean.Book;
import com.example.transport.bean.User;
import com.example.transport.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private HttpServletRequest request;


    @ResponseBody
    @RequestMapping("/findbooklistbyname")
    public List<Book> findBookList(){
        String name = request.getParameter("name");
        List<Book> list = bookService.getByName(name);
        return list;
    }

    @ResponseBody
    @RequestMapping("/findbooklistbytype")
    public List<Book> findBookListByType(){
        String type = request.getParameter("type");
        List<Book> list = bookService.getByType(type);
        return list;
    }

    @ResponseBody
    @RequestMapping("/booklist")
    public List<Book> getAll(){
            List<Book> list = bookService.getAll();
        for (Book a: list
             ) {
            System.out.println(a.getImgUrl());
        }
        return list;
    }
    @RequestMapping("/tobooklist")
    public String tobooklist(Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "booklist";
    }
    @RequestMapping("/tobooklist2")
    public String tobooklist2(Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "booklist2";
    }

    @RequestMapping("/tobookadd")
    public String tobookadd(Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "bookadd";
    }
    @RequestMapping("/bookadd")
    public String bookadd(@RequestParam("file") MultipartFile file, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路径
        String path = filePath;
        // 新建文件
        File filepath = new File(path, filename);
        System.out.println("upload:"+file+"===path:"+filePath+"===filename:"+filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("filename", "/img/"+filename);
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String num = request.getParameter("num");
        String imgUrl = "img/"+filename;
        System.out.println(imgUrl);
        bookService.add(name,content,type,num,imgUrl);
        return "booklist";
    }

    @RequestMapping("/tofindbook")
    public String tofindbook(int id, Model model){
        model.addAttribute("id",id);
        return "bookinfo";
    }
    @RequestMapping("/tofindbook2")
    public String tofindbook2(int id, Model model){
        model.addAttribute("id",id);
        return "bookinfo2";
    }
    @ResponseBody
    @RequestMapping("/findbook")
    public Book findBook(int id){
        return bookService.findBook(id);
    }

    @ResponseBody
    @RequestMapping("/bookinfo")
    public String bookinfo(String name){
        System.out.println("bookinfo:"+name);
        String msg = bookService.rent(name);
        return msg;
    }

    @RequestMapping("/bookupdate")
    public String bookupdate(int id, Model model){
        Book book = bookService.findBook(id);
        model.addAttribute("book",book);
        return "bookupdate";
    }

    @RequestMapping("/bookdelete")
    public String bookdelete(int id, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        bookService.bookDelete(id);
        return "booklist";
    }

    // 跳转上传页面
    @RequestMapping("test")
    public String test() {
        return "Page";
    }
    /**上传地址*/
    @Value("${file.upload.path}")
    private String filePath;
    // 执行上传
    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路径
        String path = filePath;
        // 新建文件
        File filepath = new File(path, filename);
        System.out.println("upload:"+file+"===path:"+filePath+"===filename:"+filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("filename", "/img/"+filename);
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String num = request.getParameter("num");
        String imgUrl = "img/"+filename;
        System.out.println(imgUrl);
        bookService.update(id,name,content,type,num,imgUrl);
        return "booklist";
    }

    @RequestMapping("update")
    public String update(Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String num = request.getParameter("num");
        String imgUrl = request.getParameter("imgUrl");
        System.out.println(imgUrl);
        bookService.update(id,name,content,type,num,imgUrl);
        return "booklist";
    }

    @RequestMapping("/tobookrent")
    public String toBookRent( Model model){
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user.getUserId());
        model.addAttribute("userId",user.getUserId());
        return "rent";
    }
    @ResponseBody
    @RequestMapping("/bookrent")
    public Book bookRent(int id){
        return bookService.findBook(id);
    }

    @ResponseBody
    @RequestMapping("/rentlist")
    public List<Book> rentlist(){
        String userId = request.getParameter("userId");
        return bookService.findRent(userId);
    }

    //归还书籍
    @ResponseBody
    @RequestMapping("/bookback")
    public List<Book> bookBack(){
        String userId = request.getParameter("userId");
        System.out.println("bookback"+userId);
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("bookback"+id);
        String name = bookService.findBook(id).getName();
        System.out.println("bookback"+name);
        bookService.back(name,userId);
        return bookService.findRent(userId);
    }
}
