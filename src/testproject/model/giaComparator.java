/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testproject.model;

import java.util.Comparator;

/**
 *
 * @author phuon
 */
public class giaComparator implements Comparator<Phong>{

    @Override
    public int compare(Phong o1, Phong o2) {
       if(o1.getGiaPhong() == o2.getGiaPhong()){
           return 0;
       }
       else if(o1.getGiaPhong() > o2.getGiaPhong()){
           return 1;
       }
       else{
           return -1;
       }
    }
    
}
