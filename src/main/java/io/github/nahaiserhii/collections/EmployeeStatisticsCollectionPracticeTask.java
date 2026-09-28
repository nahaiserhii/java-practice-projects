package io.github.nahaiserhii.collections;

import java.util.*;

public class EmployeeStatisticsCollectionPracticeTask {
    /*
     * Task: Java Collections Framework Practice
     *
     * Given a list of employees:
     *
     * 1. Find all unique departments.
     * 2. Group employees by department.
     * 3. Find the employee with the highest salary.
     * 4. Calculate the average salary for each department.
     * 5. Sort employees by salary in descending order.
     * 6. Find employees who have the same salary.
     *
     * Requirements:
     * - Use Java Collections Framework.
     * - Do not use Stream API.
     * - Keep everything in one Java file.
     */

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee("Alex", "QA", 2500),
                new Employee("John", "DEV", 3200),
                new Employee("Kate", "QA", 2800),
                new Employee("Mike", "DEV", 3500),
                new Employee("Anna", "HR", 2200),
                new Employee("Bob", "QA", 2500)
        );

        // 1. Find unique departments
        Set<String> departments = new HashSet<>();

        for (Employee employee : employees) {
            departments.add(employee.getDepartment());
        }

        System.out.println("Unique departments:");
        System.out.println(departments);

        // 2. Group employees by department
        Map<String, List<Employee>> employeesByDepartment = new HashMap<>();

        for (Employee employee : employees) {
            employeesByDepartment
                    .computeIfAbsent(
                            employee.getDepartment(),
                            key -> new ArrayList<>()
                    )
                    .add(employee);
        }

        System.out.println("\nEmployees by department:");
        System.out.println(employeesByDepartment);

        // 3. Find the employee with the highest salary
        Employee highestPaidEmployee = employees.getFirst();

        for (Employee employee : employees) {
            if (employee.getSalary() > highestPaidEmployee.getSalary()) {
                highestPaidEmployee = employee;
            }
        }

        System.out.println("\nHighest paid employee:");
        System.out.println(highestPaidEmployee);

        // 4. Calculate average salary by department
        Map<String, Double> averageSalaryByDepartment = getStringDoubleMap(employees);

        System.out.println("\nAverage salary by department:");
        System.out.println(averageSalaryByDepartment);

        // 5. Sort employees by salary in descending order
        List<Employee> sortedEmployees = new ArrayList<>(employees);

        sortedEmployees.sort(
                Comparator.comparingInt(Employee::getSalary).reversed()
        );

        System.out.println("\nEmployees sorted by salary:");

        for (Employee employee : sortedEmployees) {
            System.out.println(employee);
        }

        // 6. Find employees with the same salary
        Map<Integer, List<Employee>> employeesBySalary = new HashMap<>();

        for (Employee employee : employees) {
            employeesBySalary
                    .computeIfAbsent(
                            employee.getSalary(),
                            key -> new ArrayList<>()
                    )
                    .add(employee);
        }

        System.out.println("\nEmployees with duplicate salaries:");

        for (Map.Entry<Integer, List<Employee>> entry : employeesBySalary.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println(
                        entry.getKey() + " -> " + entry.getValue()
                );
            }
        }
    }

    private static Map<String, Double> getStringDoubleMap(List<Employee> employees) {
        Map<String, Integer> salarySumByDepartment = new HashMap<>();
        Map<String, Integer> employeeCountByDepartment = new HashMap<>();

        for (Employee employee : employees) {
            String department = employee.getDepartment();

            salarySumByDepartment.put(
                    department,
                    salarySumByDepartment.getOrDefault(department, 0)
                            + employee.getSalary()
            );

            employeeCountByDepartment.put(
                    department,
                    employeeCountByDepartment.getOrDefault(department, 0) + 1
            );
        }

        Map<String, Double> averageSalaryByDepartment = new HashMap<>();

        for (String department : salarySumByDepartment.keySet()) {
            double averageSalary =
                    (double) salarySumByDepartment.get(department)
                            / employeeCountByDepartment.get(department);

            averageSalaryByDepartment.put(department, averageSalary);
        }
        return averageSalaryByDepartment;
    }

    static class Employee {

        private final String name;
        private final String department;
        private final int salary;

        public Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getDepartment() {
            return department;
        }

        public int getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return name + " - " + department + " - " + salary;
        }
    }
}
