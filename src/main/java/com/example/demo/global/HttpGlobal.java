package com.example.demo.global;

import com.example.demo.model.*;

import java.util.HashMap;

public class HttpGlobal {
    public static String[] proverbArr = {"The grass is always greener on the other side of the fence",
            "Dont judge a book by its cover", "Strike while the iron is hot", "Too many cooks spoil the broth",
            "You cant have your cake and eat it too"};

    public static int port;
    public static String postAnswer;
    public static String getAnswer;

    public static HashMap<String, Long> sceStartTime = new HashMap<>();
    public static HashMap<String, Long> sceElapsedTime = new HashMap<>();

    public static HashMap<String, Long> statusStartTime = new HashMap<>();
    public static HashMap<String, Long> statusElapsedTime = new HashMap<>();

    public static HashMap<String, Long> headerStartTime = new HashMap<>();
    public static HashMap<String, Long> headerElapsedTime = new HashMap<>();

    public static HashMap<String, StuCliScenario> webCliSceStuInfo = new HashMap<>();
    public static HashMap<String, Boolean> hasRequestedGet = new HashMap<>();
    public static HashMap<String, Boolean> hasRequestedPost = new HashMap<>();

    public static HashMap<String, StuCliUnit> webCliUnitStuInfo = new HashMap<>();

    public static HashMap<String, Integer> statusMap = new HashMap<>();
    public static HashMap<String, StuServerInfo> stuInfo = new HashMap<>();
    public static HashMap<String, StuServerStatus> statusInfo = new HashMap<>();
    public static HashMap<String, StuServerHeader> headerInfo = new HashMap<>();

}
