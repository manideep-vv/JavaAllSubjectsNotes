package edureka;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Book {
    int id;
    String name;
    String author;
    String publisher;
    int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public Book(int id, String name, String author, String publisher, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
    }

    public static void main(String[] args) {
        Book b1 = new Book(121, "Let us C", "Yashwant Kanetkar", "BPB", 8);
        Book b2 = new Book(233, "Operating System", "Galvin", "Wiley", 6);
        Book b3 = new Book(101, "Data Communications & Networking", "Forouzan", "Mc Graw Hill", 4);
        Book b4 = new Book(121, "Let us C", "Yashwant Kanetkar", "Mc Graw Hill", 11);

        Map<Integer, Book> books = new HashMap<>();
        books.put(b1.id,b1);
        books.put(b2.id,b2);
        books.put(b3.id,b3);
        books.put(b4.id,b4);

        System.out.println(books);

        //Double the quantity of all the books.
        books.entrySet().forEach(e->{
            Book existingBookWithOldQty = e.getValue();
            existingBookWithOldQty.quantity*=2;
        });
        System.out.println("books with new quantity \n"+books);
        if(books.containsKey(233)){
            Book book233 = books.get(233);
            String pub = book233.getPublisher();
            book233.setPublisher("USA"+pub);
        }
        System.out.println("books after modifying for 233 \n"+books);
    }
}
