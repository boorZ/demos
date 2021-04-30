package com.example.sentineldemo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SentinelDemoApplicationTests {

    @Test
    void contextLoads() {

    }
    public static void main(String[] args) {
        // 不断进行资源调用.
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                // 资源中的逻辑.
                System.out.println("hello world");
            } catch (BlockException e1) {
                System.out.println("blocked!");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

}
