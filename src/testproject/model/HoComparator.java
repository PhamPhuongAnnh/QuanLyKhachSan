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
public class HoComparator implements Comparator<KhachHang> {

    @Override
    public int compare(KhachHang o1, KhachHang o2) {
        return o1.getHoTen().compareTo(o2.getHoTen());
    }
}
