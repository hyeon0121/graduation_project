package com.example.demo.global;

import com.example.demo.model.StuCliGet;
import com.example.demo.model.StuCliPost;
import com.example.demo.model.StuCliScenario;
import com.example.demo.model.StuServerInfo;

import java.util.HashMap;

public class HttpGlobal {
    public static String[] proverbArr = {"The grass is always greener on the other side of the fence.",
            "Don’t judge a book by its cover.", "Strike while the iron is hot.", "Too many cooks spoil the broth.",
            "You can’t have your cake and eat it too."};

    public static int port;
    public static boolean httpCheck = false;
    public static boolean httpVersion = false;
    public static boolean headerUserAgent = false;
    public static String postAnswer;
    public static String getAnswer;

    public static HashMap<String, StuCliScenario> webCliStuInfo = new HashMap<String, StuCliScenario>();
    public static HashMap<String, StuCliScenario> webCliSceStuInfo = new HashMap<String, StuCliScenario>();
    public static HashMap<String, StuCliGet> webCliGetStuInfo = new HashMap<String, StuCliGet>();
    public static HashMap<String, StuCliPost> webCliPostStuInfo = new HashMap<String, StuCliPost>();

    public static HashMap<String, Integer> statusMap = new HashMap<>();
    public static HashMap<String, StuServerInfo> stuInfo = new HashMap<>();



}
