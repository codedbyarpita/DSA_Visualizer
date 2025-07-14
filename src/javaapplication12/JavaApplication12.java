/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class JavaApplication12 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("DSA Visualizer");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pathfinding", new PathfindingPanel());
        tabs.add("Sorting/Searching", new SortSearchPanel());
        tabs.add("Dynamic Programming", new DPPanel());
        tabs.add("Heap", new HeapPanel());
        tabs.add("Hash Table", new HashTablePanel());
        tabs.add("Trie (Prefix Tree)", new TriePanel());

        add(tabs);
        setVisible(true);
    }
}

class DPPanel extends JPanel {
    JTextArea outputArea;

    public DPPanel() {
        setLayout(new BorderLayout());
        JButton fibButton = new JButton("Fibonacci DP");
        outputArea = new JTextArea(20, 50);
        JPanel controls = new JPanel();
        controls.add(fibButton);
        add(controls, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        fibButton.addActionListener(e -> fibonacciMenu());
    }

    private void fibonacciMenu() {
        String input = JOptionPane.showInputDialog("Enter n for Fibonacci DP:");
        if (input != null) {
            int n = Integer.parseInt(input.trim());
            int result = DPAlgorithms.fibonacciDP(n, outputArea);
            outputArea.append("\nFibonacci(" + n + ") = " + result + "\n");
        }
    }
}

class DPAlgorithms {
    public static int fibonacciDP(int n, JTextArea output) {
        int[] dp = new int[n + 1];
        if (n <= 1) return n;
        dp[0] = 0;
        dp[1] = 1;
        output.setText("Fibonacci DP Steps:\n");
        output.append("dp[0] = 0\n");
        output.append("dp[1] = 1\n");
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
            output.append("dp[" + i + "] = " + dp[i] + "\n");
        }
        return dp[n];
    }
}

class HeapPanel extends JPanel {
    JTextArea outputArea;

    public HeapPanel() {
        setLayout(new BorderLayout());
        JButton heapButton = new JButton("Demonstrate Heap with Input");
        outputArea = new JTextArea(20, 50);
        JPanel controls = new JPanel();
        controls.add(heapButton);
        add(controls, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        heapButton.addActionListener(e -> demonstrateHeapWithInput());
    }

    private void demonstrateHeapWithInput() {
        String input = JOptionPane.showInputDialog("Enter numbers separated by commas:");
        if (input != null && !input.trim().isEmpty()) {
            int[] values = Arrays.stream(input.split(","))
                                  .map(String::trim)
                                  .mapToInt(Integer::parseInt)
                                  .toArray();
            HeapAlgorithms.demonstrateHeap(values, outputArea);
        }
    }
}


class HeapAlgorithms {
    public static void demonstrateHeap(int[] values, JTextArea output) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        output.setText("Min Heap Example with User Input:\n");
        for (int v : values) {
            minHeap.offer(v);
            output.append("Added: " + v + " → " + minHeap + "\n");
        }
        while (!minHeap.isEmpty()) {
            output.append("Removed: " + minHeap.poll() + " → " + minHeap + "\n");
        }
    }
}


// Sorting & Searching Panel
class SortSearchPanel extends JPanel {
    JTextArea outputArea;

    public SortSearchPanel() {
        setLayout(new BorderLayout());
        JButton sortButton = new JButton("Sort Array");
        JButton searchButton = new JButton("Search in Array");
        outputArea = new JTextArea(20, 50);
        JPanel controls = new JPanel();
        controls.add(sortButton);
        controls.add(searchButton);
        add(controls, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        sortButton.addActionListener(e -> sortMenu());
        searchButton.addActionListener(e -> searchMenu());
    }

    private void sortMenu() {
        String input = JOptionPane.showInputDialog("Enter numbers separated by commas:");
        if (input != null) {
            int[] array = Arrays.stream(input.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            String[] options = {"Bubble", "Selection", "Insertion", "Merge", "Quick"};
            int choice = JOptionPane.showOptionDialog(this, "Choose Sorting Algorithm", "Sorting",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 : SortingAlgorithms.bubbleSort(array, outputArea);
                break;
                case 1 : SortingAlgorithms.selectionSort(array, outputArea);
                break;
                case 2 : SortingAlgorithms.insertionSort(array, outputArea);
                break;
                case 3 : SortingAlgorithms.mergeSort(array, outputArea);
                break;
                case 4 : SortingAlgorithms.quickSort(array, outputArea);
                break;
            }
        }
    }

    private void searchMenu() {
        String input = JOptionPane.showInputDialog("Enter numbers separated by commas:");
        if (input != null) {
            int[] array = Arrays.stream(input.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            int target = Integer.parseInt(JOptionPane.showInputDialog("Enter value to search:"));
            String[] options = {"Linear Search", "Binary Search"};
            int choice = JOptionPane.showOptionDialog(this, "Choose Searching Algorithm", "Searching",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 : SearchingAlgorithms.linearSearch(array, target, outputArea);
                break;
                case 1 : SearchingAlgorithms.binarySearch(array, target, outputArea);
                break;
            }
        }
    }
}

// Pathfinding Panel
class PathfindingPanel extends JPanel {
    private final int rows = 20, cols = 20;
    private final Cell[][] grid = new Cell[rows][cols];
    private Cell startCell = null, endCell = null;
    private final JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
    private final JTextArea logArea = new JTextArea(10, 30);

    public PathfindingPanel() {
        setLayout(new BorderLayout());
        JPanel controls = new JPanel();
        JButton dijkstraBtn = new JButton("Run Dijkstra");
        JButton bfsBtn = new JButton("Run BFS");
        JButton dfsBtn = new JButton("Run DFS");
        controls.add(dijkstraBtn);
        controls.add(bfsBtn);
        controls.add(dfsBtn);
        buildGrid();
        add(gridPanel, BorderLayout.CENTER);
        add(controls, BorderLayout.NORTH);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
        logArea.setEditable(false);

        dijkstraBtn.addActionListener(e -> runDijkstra());
        bfsBtn.addActionListener(e -> runBFS());
        dfsBtn.addActionListener(e -> runDFS());
    }

    private void buildGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                grid[r][c] = cell;
                gridPanel.add(cell);
            }
        }
    }

    private void resetGrid() {
        for (Cell[] row : grid) {
            for (Cell cell : row) cell.reset();
        }
        logArea.setText("");
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : dirs) {
            int r = cell.row + d[0];
            int c = cell.col + d[1];
            if (r >= 0 && r < rows && c >= 0 && c < cols) {
                neighbors.add(grid[r][c]);
            }
        }
        return neighbors;
    }

    private void runDijkstra() {
        if (startCell == null || endCell == null) return;
        resetGrid();
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        startCell.distance = 0;
        pq.add(startCell);
        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            if (current == endCell) break;
            for (Cell neighbor : getNeighbors(current)) {
                if (neighbor.isWall) continue;
                int newDist = current.distance + 1;
                if (newDist < neighbor.distance) {
                    neighbor.distance = newDist;
                    neighbor.prev = current;
                    pq.add(neighbor);
                }
            }
            current.setVisited();
            gridPanel.repaint();
            try { Thread.sleep(2); } catch (InterruptedException ignored) {}
        }
        highlightPath();
    }

    private void runBFS() {
        if (startCell == null || endCell == null) return;
        resetGrid();
        Queue<Cell> queue = new LinkedList<>();
        queue.add(startCell);
        Set<Cell> visited = new HashSet<>();
        visited.add(startCell);
        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current == endCell) break;
            for (Cell neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor) && !neighbor.isWall) {
                    neighbor.prev = current;
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
            current.setVisited();
            gridPanel.repaint();
            try { Thread.sleep(2); } catch (InterruptedException ignored) {}
        }
        highlightPath();
    }

    private void runDFS() {
        if (startCell == null || endCell == null) return;
        resetGrid();
        Stack<Cell> stack = new Stack<>();
        stack.push(startCell);
        Set<Cell> visited = new HashSet<>();
        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            if (visited.contains(current)) continue;
            visited.add(current);
            if (current == endCell) break;
            for (Cell neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor) && !neighbor.isWall) {
                    neighbor.prev = current;
                    stack.push(neighbor);
                }
            }
            current.setVisited();
            gridPanel.repaint();
            try { Thread.sleep(2); } catch (InterruptedException ignored) {}
        }
        highlightPath();
    }

    private void highlightPath() {
        Cell curr = endCell;
        while (curr != null) {
            curr.setPath();
            curr = curr.prev;
        }
    }

    class Cell extends JPanel {
        int row, col, distance = Integer.MAX_VALUE;
        boolean isWall = false;
        Cell prev = null;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (startCell == null) {
                            startCell = Cell.this;
                            setBackground(Color.GREEN);
                        } else if (endCell == null) {
                            endCell = Cell.this;
                            setBackground(Color.RED);
                        } else {
                            isWall = !isWall;
                            setBackground(isWall ? Color.BLACK : Color.WHITE);
                        }
                    }
                }
            });
        }

        void setVisited() {
            if (this != startCell && this != endCell && !isWall) setBackground(Color.CYAN);
        }

        void setPath() {
            if (this != startCell && this != endCell && !isWall) setBackground(Color.YELLOW);
        }

        void reset() {
            distance = Integer.MAX_VALUE;
            prev = null;
            if (!isWall && this != startCell && this != endCell) setBackground(Color.WHITE);
        }
    }
}
// SortingAlgorithms.java
class SortingAlgorithms {
    public static void bubbleSort(int[] arr, JTextArea output) {
        output.setText("Bubble Sort:\n");
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                output.append(Arrays.toString(arr) + "\n");
            }
        }
    }

    public static void selectionSort(int[] arr, JTextArea output) {
        output.setText("Selection Sort:\n");
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
            output.append(Arrays.toString(arr) + "\n");
        }
    }

    public static void insertionSort(int[] arr, JTextArea output) {
        output.setText("Insertion Sort:\n");
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                output.append(Arrays.toString(arr) + "\n");
            }
            arr[j + 1] = key;
            output.append(Arrays.toString(arr) + "\n");
        }
    }

    public static void mergeSort(int[] arr, JTextArea output) {
        output.setText("Merge Sort:\n");
        mergeSortHelper(arr, 0, arr.length - 1, output);
    }

    private static void mergeSortHelper(int[] arr, int l, int r, JTextArea output) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSortHelper(arr, l, m, output);
            mergeSortHelper(arr, m + 1, r, output);
            merge(arr, l, m, r, output);
        }
    }

    private static void merge(int[] arr, int l, int m, int r, JTextArea output) {
        int[] left = Arrays.copyOfRange(arr, l, m + 1);
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
            output.append(Arrays.toString(arr) + "\n");
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    public static void quickSort(int[] arr, JTextArea output) {
        output.setText("Quick Sort:\n");
        quickSortHelper(arr, 0, arr.length - 1, output);
    }

    private static void quickSortHelper(int[] arr, int low, int high, JTextArea output) {
        if (low < high) {
            int pi = partition(arr, low, high, output);
            quickSortHelper(arr, low, pi - 1, output);
            quickSortHelper(arr, pi + 1, high, output);
        }
    }

    private static int partition(int[] arr, int low, int high, JTextArea output) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                output.append(Arrays.toString(arr) + "\n");
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        output.append(Arrays.toString(arr) + "\n");
        return i + 1;
    }
}

// SearchingAlgorithms.java
class SearchingAlgorithms {
    public static void linearSearch(int[] arr, int target, JTextArea output) {
        output.setText("Linear Search:\n");
        for (int i = 0; i < arr.length; i++) {
            output.append("Checking index " + i + ": " + arr[i] + "\n");
            if (arr[i] == target) {
                output.append("Found at index: " + i + "\n");
                return;
            }
        }
        output.append("Not found\n");
    }

    public static void binarySearch(int[] arr, int target, JTextArea output) {
        output.setText("Binary Search:\n");
        Arrays.sort(arr); // Important: Binary Search requires sorted array
        output.append("Sorted array: " + Arrays.toString(arr) + "\n");
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            output.append("Checking middle index " + mid + ": " + arr[mid] + "\n");
            if (arr[mid] == target) {
                output.append("Found at index: " + mid + "\n");
                return;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        output.append("Not found\n");
    }
}
class HashTablePanel extends JPanel {
    private final JTextArea outputArea;
    private final HashMap<String, String> hashTable = new HashMap<>();

    public HashTablePanel() {
        setLayout(new BorderLayout());
        outputArea = new JTextArea(20, 50);
        JPanel controls = new JPanel();

        JButton addButton = new JButton("Add/Update Key-Value");
        JButton searchButton = new JButton("Search Key");
        JButton deleteButton = new JButton("Delete Key");

        controls.add(addButton);
        controls.add(searchButton);
        controls.add(deleteButton);

        add(controls, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addOrUpdateEntry());
        searchButton.addActionListener(e -> searchEntry());
        deleteButton.addActionListener(e -> deleteEntry());
    }

    private void addOrUpdateEntry() {
        String key = JOptionPane.showInputDialog("Enter Key (>= 0):");
        String value = JOptionPane.showInputDialog("Enter Value:");

        if (key != null && value != null) {
            try {
                int intKey = Integer.parseInt(key.trim());
                if (intKey < 0) {
                    JOptionPane.showMessageDialog(this, "Key must be greater than or equal to 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Key must be a valid number.");
                return;
            }

            // Single value per key
            if (hashTable.containsKey(key)) {
                JOptionPane.showMessageDialog(this, "Key already exists. Value will not be updated.");
            } else {
                hashTable.put(key, value);
            }
            displayTable();
        }
    }

    private void searchEntry() {
        String key = JOptionPane.showInputDialog("Enter Key to Search:");
        if (key != null) {
            String value = hashTable.get(key);
            outputArea.setText(value != null ? "Found: " + key + " → " + value : "Key not found.");
        }
    }

    private void deleteEntry() {
        String key = JOptionPane.showInputDialog("Enter Key to Delete:");
        if (key != null) {
            hashTable.remove(key);
            displayTable();
        }
    }

    private void displayTable() {
        outputArea.setText("Current Hash Table (Single Value Per Key):\n");
        for (Map.Entry<String, String> entry : hashTable.entrySet()) {
            outputArea.append(entry.getKey() + " → " + entry.getValue() + "\n");
        }
    }
}

// 2️⃣ Trie Panel and Logic
class TriePanel extends JPanel {
    private DefaultMutableTreeNode root;
    private JTree trieTree;
    private Trie trie;

    public TriePanel() {
        setLayout(new BorderLayout());
        JButton insertButton = new JButton("Insert Words");
        trie = new Trie();

        root = new DefaultMutableTreeNode("Root");
        trieTree = new JTree(root);
        add(new JScrollPane(trieTree), BorderLayout.CENTER);
        add(insertButton, BorderLayout.NORTH);

        insertButton.addActionListener(e -> insertWords());
    }

    private void insertWords() {
        String input = JOptionPane.showInputDialog("Enter words separated by commas:");
        if (input != null && !input.isEmpty()) {
            String[] words = input.split(",");
            for (String word : words) {
                trie.insert(word.trim());
            }
            updateTree();
        }
    }

    private void updateTree() {
        root.removeAllChildren();
        buildTree(trie.root, root);
        ((DefaultTreeModel) trieTree.getModel()).reload();
    }

    private void buildTree(TrieNode trieNode, DefaultMutableTreeNode treeNode) {
        for (Map.Entry<Character, TrieNode> entry : trieNode.children.entrySet()) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(entry.getKey());
            treeNode.add(child);
            buildTree(entry.getValue(), child);
        }
    }
}

// Trie and TrieNode classes:
class Trie {
    TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEnd = true;
    }
}

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
}
