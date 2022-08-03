#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <stdbool.h>

typedef struct Node_t
{
    int             data;           // 数据域
    struct Node_t  *next;           // 指针域
} Node_t;

typedef struct Stack_t
{
    struct Node_t  *top;            // 栈顶
    struct Node_t  *button;         // 栈底
} Stack_t;

/* 是否为空 */
bool is_empty(struct Stack_t *stack)
{
    return stack->top == stack->button;
}

/* 初始化 */
void init(struct Stack_t *stack)
{
    stack->top = malloc(sizeof(struct Node_t));
    if (NULL == stack->top)
    {
        printf("stack initial fail\n");
        exit(1);
    }
    // 申请栈顶即可，初始化时栈顶和栈底指向同一块区域，并且栈顶的next = NULL，避免野指针
    stack->button = stack->top;
    stack->top->next = NULL;
}

/* 入栈 */
bool push(struct Stack_t *stack, int data)
{
    struct Node_t *node = malloc(sizeof(struct Node_t));
    if (NULL == node)
    {
        printf("node malloc fail\n");
        exit(1);
    }
    node->data = data;
    node->next = NULL;

    // 头插法，先连后断
    node->next = stack->top;
    stack->top = node;
    return true;
}

/*
 * 出栈, 先入后出
 * @param delete_data 返回删除的值
 */
bool pop(struct Stack_t *stack, int *delete_data)
{
    if (is_empty(stack))
    {
        return false;
    }

    struct Node_t *tmp = stack->top;
    *delete_data = stack->top->data;
    stack->top = stack->top->next;
    free(tmp);
    tmp = NULL;
    return true;
}

/* 打印栈 */
void traverse(struct Stack_t *stack)
{
    if (is_empty(stack))
    {
        return;
    }
    struct Node_t *tmp = stack->top;
    while (tmp != stack->button)
    {
        printf("%d\t", tmp->data);
        tmp = tmp->next;
    }
    printf("\n");
}

/* 清除栈 */
void clear(struct Stack_t *stack)
{
    if (is_empty(stack))
    {
        return;
    }
    struct Node_t *tmp = stack->top;
    while (stack->top != stack->button)
    {
        tmp = stack->top;
        // next
        stack->top = stack->top->next;
        free(tmp);
    }
    // 初始化
    stack->top = stack->button;
    tmp = NULL;
}

int main(int argc, char **argv)
{
    Stack_t stack;
    init(&stack);
    push(&stack, 6);
    push(&stack, 3);
    push(&stack, 5);
    push(&stack, 7);
    traverse(&stack);

    int delete_data;
    pop(&stack, &delete_data);
    printf("delete_data:%d\n", delete_data);
    pop(&stack, &delete_data);
    printf("delete_data:%d\n", delete_data);
    traverse(&stack);

    clear(&stack);
    printf("after clear stack\n");
    traverse(&stack);

    printf("push into stack\n");
    push(&stack, 7);
    traverse(&stack);
}
