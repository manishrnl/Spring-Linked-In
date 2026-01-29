package com.example.post_service.auth;

public class UserContextHolder {

//    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();
//    In your UserContextHolder.java, you are using ThreadLocal. If your code ever uses @Async methods or parallel streams,
//    the User ID will be lost because those run on different threads.
    private static final ThreadLocal<Long> currentUserId = new InheritableThreadLocal<>();

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }

    public static void setCurrentUserId(Long userId) {
        currentUserId.set(userId);
    }

    static void clear() {
        currentUserId.remove();
    }
}
