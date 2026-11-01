# Fast-Food Tray Storage System

CA1 group assignment – **Algorithms and Architecture** (CCT College Dublin)
Language: **Java** · IDE: **NetBeans**

## Problem
A fast-food restaurant stores five party foods (Burger, Pizza, Fries, Sandwich, Hotdog)
in a rectangular storage unit with **two sides** and a maximum of **8 trays**. The chef either:

1. adds and removes trays from the **front** side (LIFO – Stack behaviour), or
2. adds at the **front** and removes from the **opposite** side (FIFO – Queue behaviour).

Each tray records the food **name**, **weight (g)**, **best-before date** (max 14 days)
and the **time it was placed** into storage.

## Chosen data structure: Deque (Double-Ended Queue)
A deque allows insertion and removal at **both ends in O(1)**, so one structure supports
both the Stack mode and the Queue mode required by the scenario. It is implemented with
Java's `ArrayDeque`, and the 8-tray limit is enforced manually in `Storage.add()`.

## Classes
| Class | Responsibility |
|---|---|
| `FoodItem` | Immutable model of one tray (name, weight, best-before, time placed) |
| `Storage` | Deque-based storage unit, capacity 8 (add, remove, peek, search, display, status) |
| `Validator` | Validates name, weight and date input |
| `FoodStorageFullException` | Thrown when adding to a full storage |
| `FoodStorageEmptyException` | Thrown when removing / peeking / searching an empty storage |
| `FoodStorageApp` | Menu-driven console application (`main`) |

## Time complexity
| Operation | Method | Complexity |
|---|---|---|
| Add (front) | `addFirst` | O(1) amortised |
| Remove front (Stack) | `removeFirst` | O(1) |
| Remove opposite (Queue) | `removeLast` | O(1) |
| Peek top | `peekFirst` / `peekLast` | O(1) |
| isEmpty / isFull / size | – | O(1) |
| Search by name | linear scan | O(n) |
| Display all | linear scan | O(n) |
| Status report | linear scan | O(n) |

Space complexity: O(n), bounded by n ≤ 8.

## Validation
- Food name must be one of the 5 allowed items (case-insensitive)
- Weight must be a number between 1 and 5000 g
- Best-before date must be a real date in `dd/MM/yyyy`, not in the past, max 14 days ahead
- Adding to a full storage, or removing/peeking/searching an empty storage, shows an error
- Invalid menu input re-prompts the user

## How to run
1. Open the project in NetBeans.
2. Run the main class `FoodStorageApp` (**F6**).

## Group members
| Name | Student number |
|---|---|
| Tsolmon Jargalsaikhan | 2024520 |
| Tuguldur Azzaya | 2024418 |
| Khongorzul Oktyabri | 2024259 |

## Use of Generative AI
We used generative AI tools in this assignment, as follows:

1. **Code:** Claude (Anthropic) was used to review and debug our Java code and to suggest
   improvements such as strict date validation and custom exception classes.
   ChatGPT was used to find mistakes in our code, which we then fixed ourselves.
2. **Poster:** Claude helped draft the poster layout and text; ChatGPT was used to count
   words and give feedback.
3. **Testing:** We ran all 15 validation test cases ourselves in NetBeans and took
   screenshots. Those screenshots are the evidence on the poster.

All AI output was checked, tested and edited by the group.
