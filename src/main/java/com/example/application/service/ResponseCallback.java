package com.example.application.service;

@FunctionalInterface
public interface ResponseCallback<T> {
    void operationFinished(T results);
}
