package main.controllers;

import main.Repositories.LinesRepository;
import main.Services.FileHandler;
import main.model.Lines;
import main.model.Result;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Semaphore;

@Controller

@RequestMapping("/hello")
public class TestController {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private LinesRepository repository;

    @Autowired
    FileHandler fileHandler;

    @Autowired
    Result result;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @GetMapping
    @Transactional(readOnly = true,isolation = Isolation.DEFAULT)
    public @ResponseBody Set<Result> printHello(ModelMap model) {
        List<Object[]> list = sessionFactory.getCurrentSession().createSQLQuery("SELECT TEXT, COUNT(*) c FROM word_lines group by TEXT HAVING c>1;").list();
        Set<Result> resultSet = new HashSet<>();
        for(Object[] objects : list){
            String line = (String)objects[0];
            BigInteger count = (BigInteger)objects[1];
            resultSet.add(new Result(line,count));
        }
        return resultSet;
    }

    @RequestMapping("/uploadMultipleFile")

    public @ResponseBody String uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) throws IOException {
        Semaphore semaphore = new Semaphore(3,true);
        for (MultipartFile file : files) {
            fileHandler = new FileHandler();
            fileHandler.setFile(file);
            fileHandler.setSemaphore(semaphore);
            fileHandler.setRepository(repository);
            new Thread(fileHandler).start();
        }
        return "Success";
    }


}
