#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <assert.h>
#include <string.h>

#define PAGE_SIZE 4096
#define MY_HEAP_SIZE PAGE_SIZE * 10

char *my_heap;

typedef struct {
    // size in bytes
    size_t size;

    // 1 when free, 0 else
    long int is_free;

} block_info;


void new_heap() {
    my_heap = mmap(NULL, MY_HEAP_SIZE, PROT_READ | PROT_WRITE | PROT_EXEC, MAP_ANONYMOUS | MAP_PRIVATE, -1, 0);
    block_info *struct_ptr = (block_info *) my_heap;
    struct_ptr->is_free = 1;
    struct_ptr->size = MY_HEAP_SIZE - sizeof(block_info);

    printf("mmap: %p\n", my_heap);

    if (my_heap == MAP_FAILED) {
        printf("bad mmap");
    }
}


void *my_malloc(size_t size) {
    if (size == 0 || size > (MY_HEAP_SIZE - sizeof(block_info))) {
        return NULL;
    }
    block_info *struct_ptr = (block_info *) my_heap;

    while (struct_ptr < ((block_info *) (my_heap + MY_HEAP_SIZE))) {
        if (struct_ptr->is_free == 1) {

            if (struct_ptr->size == size) {

                struct_ptr->is_free = 0;
                struct_ptr->size = size;
                return ((char *) struct_ptr + sizeof(block_info));

            } else if (struct_ptr->size > (size + sizeof(block_info))) {

                size_t cur_block_size = struct_ptr->size;
                struct_ptr->is_free = 0;
                struct_ptr->size = size;

                block_info *next_struct_ptr = (block_info *) ((char *) struct_ptr + size + sizeof(block_info));
                next_struct_ptr->size = cur_block_size - size - sizeof(block_info);
                next_struct_ptr->is_free = 1;

                return ((char *) struct_ptr + sizeof(block_info));
            }
        }
        struct_ptr = (block_info *) ((char *) struct_ptr + (sizeof(block_info) + struct_ptr->size));
    }
    return NULL;
}

void my_free(void *ptr) {
    block_info *struct_ptr = (block_info *) (ptr - (sizeof(block_info)));
    struct_ptr->is_free = 1;
    block_info *next_struct_ptr = ptr + struct_ptr->size;
    if (next_struct_ptr->is_free == 1) {
        struct_ptr->size += (next_struct_ptr->size + (sizeof(block_info)));
    }
}

void *my_calloc(size_t size) {
    char *ptr = (char *) my_malloc(size);
    memset(ptr, 0, size);
    return ptr;
}


void test_my_malloc() {
    new_heap();

    // Test 1: allocate and free a single block
    char *ptr1 = (char *) my_malloc(10);
    assert(ptr1 != NULL);
    memset(ptr1, 'c', 10);
    my_free(ptr1);

    // Test 2: allocate and free two blocks
    char *ptr2 = (char *) my_malloc(20);
    assert(ptr2 != NULL);
    memset(ptr2, 'd', 20);
    char *ptr3 = (char *) my_malloc(30);
    assert(ptr3 != NULL);
    memset(ptr3, 'e', 30);
    my_free(ptr2);
    my_free(ptr3);

    // Test 3: allocate and free a block in the middle
    char *ptr4 = (char *) my_calloc(40);
    assert(ptr4 != NULL);
    char *ptr5 = (char *) my_malloc(50);
    assert(ptr5 != NULL);
    char *ptr6 = (char *) my_malloc(60);
    assert(ptr6 != NULL);
    my_free(ptr5);
    char *ptr7 = (char *) my_malloc(70);
    assert(ptr7 != NULL);
    my_free(ptr6);
    my_free(ptr4);
    my_free(ptr7);

    // Test 4: allocate a block that is too large
    char *ptr8 = (char *) my_malloc(MY_HEAP_SIZE + 1);
    assert(ptr8 == NULL);

    printf("All tests passed!\n");
}

int main() {
    printf("Pid: %d\n", getpid());

   // test_my_malloc();



    return 0;
}


