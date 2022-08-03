#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <stdbool.h>

/* 循环队列 */
typedef struct Queue_t
{
    int *addr;        // 数组首地址
    int  front;       // 队首
    int  rear;        // 队尾
    int  length;      // 数组的容量
} Queue_t;

/* 是否为空 */
bool is_empty(struct Queue_t *queue)
{
    return queue->front == queue->rear;
}

/* 容量是否已满 */
bool is_full(struct Queue_t *queue)
{
    return queue->front == (queue->rear + 1) % queue->length;
}

void init(struct Queue_t *queue, int length)
{
    queue->addr = malloc(sizeof(int) * length);
    if (NULL == queue->addr)
    {
        printf("queue initial fail\n");
        exit(1);
    }
    queue->front = 0;
    queue->rear = 0;
    queue->length = length;
}

/** 入队 */
bool push(struct Queue_t *queue, int data)
{
    if (is_full(queue))
    {
        return false;
    }
    // 队尾插入
    queue->addr[queue->rear] = data;
    queue->rear = (queue->rear + 1) % queue->length;
    return true;
}

/*
 * 出队, 先进先出
 * @param delete_data 返回删除的值
 */
bool pop(struct Queue_t *queue, int *delete_data)
{
    if (is_empty(queue))
    {
        return false;
    }
    // 队首删除
    *delete_data = queue->addr[queue->front];
    queue->front = (queue->front + 1) % queue->length;
    return true;
}

/* 打印队列 */
void traverse(struct Queue_t *queue)
{
    if (is_empty(queue))
    {
        return;
    }
    int i = queue->front;
    while(i != queue->rear)
    {
        printf("%d\t", queue->addr[i]);
        i = (i+1) % queue->length;
    }
    printf("\n");
}

int main(int argc, char **argv)
{
    Queue_t queue;
    init(&queue, 5);

    push(&queue, 1);
    push(&queue, 2);
    push(&queue, 3);

    traverse(&queue);
    int delete_data;
    pop(&queue, &delete_data);
    printf("delete_data:%d\n", delete_data);

    traverse(&queue);

}
