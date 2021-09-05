package customermailapplication;

public class CustomerFactory {

    public static Customer createCustomer(String type) {
        switch (type) {
            case "Regular":
                return new RegularCustomer();
            case "Mountain":
                return new MountainCustomer();
            case "Delinquent":
                return new DelinquentCustomer();
        }
        return null;
    }
}
