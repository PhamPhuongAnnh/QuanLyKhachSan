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
public class MaPhongComparator implements Comparator<Phong> {

    @Override
    public int compare(Phong o1, Phong o2) {
        int item1 = Integer.parseInt(o1.getMaPhong().substring(2));
        int item2 = Integer.parseInt(o2.getMaPhong().substring(2));

        if (item1 == item2) {
            return 0;
        } else if (item1 > item2) {
            return 1;
        } else {
            return -1;
        }
    }

}
