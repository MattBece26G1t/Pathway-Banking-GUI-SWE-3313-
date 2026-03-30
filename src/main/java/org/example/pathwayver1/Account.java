package org.example.pathwayver1;

public class Account {
    private double balance;

    public Account(double balance){
        this.balance = balance;
    }

    public void deposit(double amount){
        if (amount <= 0){
            System.out.println("Invalid deposit amount.");
            return;
        }
        balance+= amount;
        System.out.println("Deposit was successful.");
    }
    public void withdraw(double amount){
        if (amount <= 0){
            System.out.println("Invalid withdrawal amount.");
            return;
        }
        if (amount > balance){
            System.out.println("Not enough funds.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawal was successful.");
    }
    public double getBalance(){
        return balance;
    }



}