#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <stdbool.h>

typedef struct Array_t
{
   int *addr;               // 数组的首地址
   int  length;             // 数组的容量
   int  effective_num;      // 数组当前的有效个数
} Array_t;

/* 限制插入的位置只能是0~effective_num-1 */
bool check_range(struct Array_t *array, int index)
{
    assert(index >= 0 && index < array->effective_num);
}

/* 检查是否容量已满 */
bool check_length(struct Array_t *array)
{
    assert(array->effective_num < array->length);
}

/* 初始化 */
void init(struct Array_t *array, int length)
{
    array->addr = malloc(sizeof(int) * length);
    if (NULL == array->addr)
    {
        printf("array initial fail\n");
        exit(1);
    }
    array->length = length;
    array->effective_num = 0;
    return;
}

/* 添加 */
bool add(struct Array_t *array, int e)
{
    check_length(array);
    (array->effective_num)++;
    // 添加元素
    array->addr[array->effective_num - 1] = e;
    return true;
}

/*
 * 插入
 * @param index 插入的目标位置，从0开始
 */
bool insert(struct Array_t *array, int index, int e)
{
    check_range(array, index);
    check_length(array);

    (array->effective_num)++;
    for (int i = array->effective_num - 1; i >= index; i--)
    {
        array->addr[i] = array->addr[i - 1];
    }
    array->addr[index] = e;
}

/*
 * 获取
 * @param index 数组位置，从0开始
 */
int get(struct Array_t *array, int index)
{
    if (index < 0 || index > array->effective_num - 1)
    {
        printf("illegal index\n");
    }
    return array->addr[index];
}

int main(int argc, char **argv)
{
    Array_t array;
    init(&array, 10);
    add(&array, 1);
    add(&array, 2);
    add(&array, 3);
    insert(&array, 0, 0);
    int e = get(&array, 0);
    printf("%d\n", e);

    for(int i = 0; i < array.effective_num; i++)
    {
        printf("index:%d, value:%d\n", i, array.addr[i]);
    }
    insert(&array, 5, 0);
}
