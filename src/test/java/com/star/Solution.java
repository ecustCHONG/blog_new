package com.star;

import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> list = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        int index = 0;
        for(String str: strs){
            int size = list.size();
            boolean success = false;
            while(size>0){
                Set<Map.Entry<String, Integer>> entries = map.entrySet();
                Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if(isSyn(next.getKey(),str)){
                        list.get(next.getValue()).add(str);
                        success=true;
                    }
                }
            }
            if(!success){
                list.add(new ArrayList<String>());
                map.put(str,index);
                list.add(new ArrayList<>());
                list.get(index++).add(str);
            }
        }
    }
    public boolean isSyn(String s,String e){
        Set<Character> set = new HashSet<>();
        for(int i = 0;i<s.length();i++){
            set.add(s.charAt(i));
        }
        for(int i = 0;i<s.length();i++){
            if(!set.contains(e.charAt(i)))
                return false;
        }
        return true;
    }
}