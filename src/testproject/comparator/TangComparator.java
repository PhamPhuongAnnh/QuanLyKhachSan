/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testproject.comparator;

import java.util.Comparator;
import testproject.model.Phong;

/**
 *
 * @author phuon
 */
public class TangComparator implements Comparator<Phong> {

    @Override
    public int compare(Phong o1, Phong o2) {
        return o1.getSoTang() - o2.getSoTang();
    }

}
