package com.heima.schedule;

import com.heima.common.constants.ScheduleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.schedule.dtos.Task;
import com.heima.schedule.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 延迟任务测试
 */
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;


    @Test
    void testTaskNow(){

        Task task = new Task();
        task.setTaskType(1);
        task.setPriority(2);
        task.setParameters(new byte[]{12,31,21});
        task.setExecuteTime(System.currentTimeMillis());

        taskService.addTask(task);

    }

    @Test
    void testTaskFuture(){
        //两分钟之后发布
        Task task1= new Task();
        task1.setTaskType(1);
        task1.setPriority(1);
        task1.setParameters(new byte[]{12,31,21});
        task1.setExecuteTime(System.currentTimeMillis()+1000 * 60 *2);
        //三分钟之后发布
        Task task2 = new Task();
        task2.setTaskType(1);
        task2.setPriority(2);
        task2.setParameters(new byte[]{81,23,14});
        task2.setExecuteTime(System.currentTimeMillis()+1000*60*3);
        Task task3 = new Task();
        task3.setTaskType(1);
        task3.setPriority(3);
        task3.setParameters(new byte[]{81,23,14});
        task3.setExecuteTime(System.currentTimeMillis()+1000*60*3);


        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.addTask(task3);
    }

    @Resource
    private CacheService cacheService;
    @Test
    public void refresh() {
        System.out.println(System.currentTimeMillis() / 1000 + "执行了定时任务");

        // 获取所有未来数据集合的key值
        Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");// future_*
        for (String futureKey : futureKeys) { // future_250_250

            String topicKey = ScheduleConstants.TOPIC + futureKey.substring(futureKey.indexOf("_"));
            //获取该组key下当前需要消费的任务数据
            Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());
            if (!tasks.isEmpty()) {
                //将这些任务数据添加到消费者队列中
                cacheService.refreshWithPipeline(futureKey, topicKey, tasks);
                System.out.println("成功的将" + futureKey + "下的当前需要执行的任务数据刷新到" + topicKey + "下");
            }
        }
    }





}
