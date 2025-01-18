package tool;

import data.Employee;

import java.util.List;

public class SortEmployee {

    public static void ascSortSalary(List<Employee> employees) {
        Employee big = null, small = null;
        for (int i = 0; i < employees.size() - 1; i++) {
            for (int j = i + 1; j < employees.size(); j++) {

                if (employees.get(i).getSalary() <= employees.get(j).getSalary()) {
                    small = employees.get(i);
                    big = employees.get(j);
                }
                employees.set(i, big);
                employees.set(j, small);
            }
        }
    }

    public static void descSortSalary(List<Employee> employees)
    {
        Employee big = null, small = null;
        for (int i = 0; i < employees.size() - 1; i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                if (employees.get(i).getSalary() >= employees.get(j).getSalary()) {
                    small = employees.get(i);
                    big = employees.get(j);
                }
                employees.set(i, big);
                employees.set(j, small);
            }
        }
    }
}
