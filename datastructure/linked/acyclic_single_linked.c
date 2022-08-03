#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <stdbool.h>

typedef struct Linked_t
{
    int              data;          // 数据域
    struct Linked_t *next;          // 指针域
} Linked_t;

/* 是否为空 */
bool isEmpty(struct Linked_t *head)
{
    return NULL == head->next;
}

/* 链表长度 */
int length(struct Linked_t *head)
{
    if (isEmpty(head))
    {
        return 0;
    }
    int length = 0;
    struct Linked_t *tmp = head->next;
    while (NULL != tmp)
    {
        length++;
        tmp = tmp->next;
    }
    return length;
}

/* 限制插入的位置只能是0~length-1 */
bool check_range(struct Linked_t *head, int index)
{
    assert(index >= 0 && index < length(head));
}

/*
 * 初始化
 * @return head节点，head节点是不存储数据的，区分头节点和首节点
 */
struct Linked_t *init(void)
{
    struct Linked_t *head = malloc(sizeof(struct Linked_t));
    if (NULL == head)
    {
        printf("linked initial fail\n");
        exit(1);
    }
    // 指针域赋值，避免野指针
    head->next = NULL;
    return head;
}

/* 头插法 */
bool insert_to_head(struct Linked_t *head, int data)
{
    struct Linked_t *node = malloc(sizeof(struct Linked_t));
    if (NULL == node)
    {
        printf("node malloc fail\n");
        exit(1);
    }
    node->data = data;
    node->next = NULL;
    if (isEmpty(head))
    {
        head->next = node;
    }
    else
    {
        // 先连后断
        node->next = head->next;
        head->next = node;
    }
    return true;
}

/*
 * 指定位置插入
 * @param index 插入位置，从0开始
 */
bool insert(struct Linked_t *head, int index, int data)
{
    check_range(head, index);
    struct Linked_t *node = malloc(sizeof(struct Linked_t));
    if (NULL == node)
    {
        printf("node malloc fail\n");
        exit(1);
    }
    node->data = data;
    node->next = NULL;
    if (0 == index)
    {
        // 先连后断
        node->next = head->next;
        head->next = node;
        return true;
    }

    int i;
    struct Linked_t *tmp;
    for (i = 0, tmp = head->next; NULL != tmp; tmp = tmp->next, i++)
    {
        if (i == index)
        {
            node->next = tmp->next;
            tmp->next = node;
            return true;
        }
    }
    return false;
}

/*
 * 指定位置删除
 * @param index 插入位置，从0开始
 * @param delete_data 返回删除的值
 */
bool delete(struct Linked_t *head, int index, int *delete_data)
{
    check_range(head, index);
    struct Linked_t *tmp;
    if (0 == index)
    {
        tmp = head->next;
        *delete_data = tmp->data;
        // 先连后断
        tmp->next = head->next->next;
        head->next = tmp->next;
        free(tmp);
        tmp = NULL;
        return true;
    }

    int i;
    struct Linked_t *p_delete_node;
    for (i = 0, tmp = head->next; NULL != tmp; tmp = tmp->next, i++)
    {
        // 获取前一个节点
        if (i+1 >= index)
        {
            p_delete_node = tmp->next;
            *delete_data = p_delete_node->data;
            tmp->next = tmp->next->next;
            free(p_delete_node);
            p_delete_node = NULL;
            tmp = NULL;
            return true;
        }
    }

    return false;
}

/* 冒泡排序 asc */
void sort(struct Linked_t *head)
{
    if (isEmpty(head))
    {
        return;
    }

    int i, j, k;
    int size = length(head);
    struct Linked_t *tmp;
    // 外层循环，只需要比较length-1次
    for (i = 0, j = size - 1; i < size - 1; i++, j--)
    {
        // 内层循环，只需要获取下标length-2终止，即倒数第二个节点，最后一个节点通过->next获取
        for (k = 0, tmp = head->next; k < j; k++, tmp = tmp->next)
        {
            if (tmp->data > tmp->next->data)
            {
                int tmp_data = tmp->data;
                tmp->data = tmp->next->data;
                tmp->next->data = tmp_data;
            }
        }
    }
}

/* 打印链表 */
void traverse(struct Linked_t *head)
{
    if (isEmpty(head))
    {
        return;
    }
    struct Linked_t *tmp = head->next;
    while (NULL != tmp)
    {
        printf("%d\t", tmp->data);
        tmp = tmp->next;
    }
    printf("\n");
}

/* 反转链表 */
void reversed(struct Linked_t *head)
{
    if (isEmpty(head))
    {
        return;
    }
    struct Linked_t *prev = NULL;
    struct Linked_t *next = NULL;
    struct Linked_t *tmp = head->next;
    /* eg: head——>A——>B——>C */
     while (NULL != tmp)
     {
         // B 保存下一个节点
         next = tmp->next;
         // A->next = NULL
         tmp->next = prev;
         // A
         prev = tmp;
         // tmp next
         tmp = next;
     }
    head->next = prev;
}

int main(int argc, char **argv)
{
    struct Linked_t *head = init();
    insert_to_head(head, 16);
    insert_to_head(head, 2);
    insert_to_head(head, 9);
    insert_to_head(head, 6);
    printf("length:%d\n", length(head));
    printf("before sort\n");
    traverse(head);
    sort(head);
    printf("after sort\n");
    traverse(head);
    insert(head, 0, 0);
    traverse(head);

    int delete_data;
    delete(head, 1, &delete_data);
    printf("delete_data:%d\n", delete_data);
    traverse(head);

    reversed(head);
    printf("after reversed\n");
    traverse(head);
}
