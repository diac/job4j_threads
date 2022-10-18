package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class AccountStorage {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean result = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(int id) {
        boolean result = false;
        if (accounts.containsKey(id)) {
            accounts.remove(id);
            result = true;
        }
        return result;
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> account = Optional.empty();
        if (accounts.containsKey(id)) {
            account = Optional.of(accounts.get(id));
        }
        return account;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (accounts.keySet().containsAll(Set.of(fromId, toId))) {
            Account fromAccount = accounts.get(fromId);
            Account toAccount = accounts.get(toId);
            if (fromAccount.amount() > 0) {
                accounts.put(fromId, new Account(fromId, fromAccount.amount() - amount));
                accounts.put(toId, new Account(toId, toAccount.amount() + amount));
                result = true;
            }
        }
        return result;
    }
}
