package com.demo.real_estate_backend.config;

import com.demo.real_estate_backend.model.*;
import com.demo.real_estate_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, 
                                   PropertyRepository propertyRepository,
                                   PropertyImageRepository imageRepository,
                                   FavoriteRepository favoriteRepository,
                                   SubscriptionRepository subscriptionRepository) {
        return args -> {
            // Check if data already exists to avoid duplicate entries
            if (userRepository.findByEmail("john.smith@realestate.com").isPresent()) {
                System.out.println("✅ Sample data already exists in database. Skipping initialization.");
                return;
            }

            // Create Users (Agents/Owners)
            User agent1 = new User();
            agent1.setFirstName("John");
            agent1.setLastName("Smith");
            agent1.setEmail("john.smith@realestate.com");
            agent1.setPassword("password123");
            agent1.setPhone("(555) 123-4567");
            agent1.setUserType(UserType.AGENT);
            agent1.setRole(Role.ADMIN);
            agent1.setSubscriptionType(SubscriptionType.PREMIUM);
            agent1.setCompany("Prime Real Estate");
            agent1.setLicenseNumber("RE-12345");
            agent1.setBio("Experienced real estate agent with 10+ years in the industry.");
            agent1.setActive(true);
            agent1 = userRepository.save(agent1);
            
            User agent2 = new User();
            agent2.setFirstName("Sarah");
            agent2.setLastName("Johnson");
            agent2.setEmail("sarah.johnson@realestate.com");
            agent2.setPassword("password123");
            agent2.setPhone("(555) 987-6543");
            agent2.setUserType(UserType.AGENT);
            agent2.setRole(Role.USER);
            agent2.setSubscriptionType(SubscriptionType.BASIC);
            agent2.setCompany("Elite Properties");
            agent2.setLicenseNumber("RE-67890");
            agent2.setBio("Specializing in luxury homes and commercial properties.");
            agent2.setActive(true);
            agent2 = userRepository.save(agent2);
            
            User owner1 = new User();
            owner1.setFirstName("Michael");
            owner1.setLastName("Brown");
            owner1.setEmail("michael.brown@email.com");
            owner1.setPassword("password123");
            owner1.setPhone("(555) 456-7890");
            owner1.setUserType(UserType.OWNER);
            owner1.setRole(Role.USER);
            owner1.setSubscriptionType(SubscriptionType.FREE);
            owner1.setActive(true);
            owner1 = userRepository.save(owner1);
            
            // Create a buyer user
            User buyer1 = new User();
            buyer1.setFirstName("Emily");
            buyer1.setLastName("Davis");
            buyer1.setEmail("emily.davis@email.com");
            buyer1.setPassword("password123");
            buyer1.setPhone("(555) 321-7654");
            buyer1.setUserType(UserType.BUYER);
            buyer1.setRole(Role.USER);
            buyer1.setSubscriptionType(SubscriptionType.BASIC);
            buyer1.setActive(true);
            buyer1 = userRepository.save(buyer1);
            
            // Create Properties with relationships
            Property property1 = new Property();
            property1.setTitle("Beautiful Family Home in Suburbs");
            property1.setDescription("Stunning 4-bedroom family home with modern amenities, spacious backyard, and located in a quiet neighborhood. Perfect for families looking for comfort and convenience.");
            property1.setPrice(new BigDecimal("550000"));
            property1.setAddress("123 Maple Street");
            property1.setCity("Springfield");
            property1.setState("IL");
            property1.setZipCode("62701");
            property1.setPropertyType(PropertyType.HOUSE);
            property1.setListingType(ListingType.FOR_SALE);
            property1.setBedrooms(4);
            property1.setBathrooms(3);
            property1.setSquareFeet(new BigDecimal("2500"));
            property1.setYearBuilt(2015);
            property1.setAvailable(true);
            property1.setOwner(agent1);
            property1 = propertyRepository.save(property1);
            
            // Add images for property1
            PropertyImage img1_1 = new PropertyImage();
            img1_1.setImageUrl("https://images.unsplash.com/photo-1568605114967-8130f3a36994");
            img1_1.setCaption("Front view of the house");
            img1_1.setIsPrimary(true);
            img1_1.setDisplayOrder(1);
            img1_1.setProperty(property1);
            imageRepository.save(img1_1);
            
            PropertyImage img1_2 = new PropertyImage();
            img1_2.setImageUrl("https://images.unsplash.com/photo-1600596542815-ffad4c1539a9");
            img1_2.setCaption("Spacious living room");
            img1_2.setIsPrimary(false);
            img1_2.setDisplayOrder(2);
            img1_2.setProperty(property1);
            imageRepository.save(img1_2);
            
            Property property2 = new Property();
            property2.setTitle("Downtown Luxury Apartment");
            property2.setDescription("Modern 2-bedroom apartment in the heart of downtown. Features include high ceilings, hardwood floors, and panoramic city views. Walking distance to restaurants and shops.");
            property2.setPrice(new BigDecimal("2500"));
            property2.setAddress("456 Oak Avenue, Unit 5B");
            property2.setCity("Chicago");
            property2.setState("IL");
            property2.setZipCode("60601");
            property2.setPropertyType(PropertyType.APARTMENT);
            property2.setListingType(ListingType.FOR_RENT);
            property2.setBedrooms(2);
            property2.setBathrooms(2);
            property2.setSquareFeet(new BigDecimal("1200"));
            property2.setYearBuilt(2020);
            property2.setAvailable(true);
            property2.setOwner(agent2);
            property2 = propertyRepository.save(property2);
            
            PropertyImage img2_1 = new PropertyImage();
            img2_1.setImageUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267");
            img2_1.setCaption("Modern apartment exterior");
            img2_1.setIsPrimary(true);
            img2_1.setDisplayOrder(1);
            img2_1.setProperty(property2);
            imageRepository.save(img2_1);
            
            Property property3 = new Property();
            property3.setTitle("Charming Townhouse with Garden");
            property3.setDescription("Lovely 3-bedroom townhouse with a private garden. Recently renovated with new kitchen and bathrooms. Great location near schools and parks.");
            property3.setPrice(new BigDecimal("425000"));
            property3.setAddress("789 Pine Road");
            property3.setCity("Naperville");
            property3.setState("IL");
            property3.setZipCode("60540");
            property3.setPropertyType(PropertyType.TOWNHOUSE);
            property3.setListingType(ListingType.FOR_SALE);
            property3.setBedrooms(3);
            property3.setBathrooms(2);
            property3.setSquareFeet(new BigDecimal("1800"));
            property3.setYearBuilt(2010);
            property3.setAvailable(true);
            property3.setOwner(owner1);
            property3 = propertyRepository.save(property3);
            
            PropertyImage img3_1 = new PropertyImage();
            img3_1.setImageUrl("https://images.unsplash.com/photo-1564013799919-ab600027ffc6");
            img3_1.setCaption("Beautiful townhouse");
            img3_1.setIsPrimary(true);
            img3_1.setDisplayOrder(1);
            img3_1.setProperty(property3);
            imageRepository.save(img3_1);
            
            Property property4 = new Property();
            property4.setTitle("Spacious Condo with Amenities");
            property4.setDescription("2-bedroom condo in a luxury building with pool, gym, and concierge service. Modern finishes and great natural light.");
            property4.setPrice(new BigDecimal("380000"));
            property4.setAddress("321 Elm Street, Unit 12C");
            property4.setCity("Evanston");
            property4.setState("IL");
            property4.setZipCode("60201");
            property4.setPropertyType(PropertyType.CONDO);
            property4.setListingType(ListingType.FOR_SALE);
            property4.setBedrooms(2);
            property4.setBathrooms(2);
            property4.setSquareFeet(new BigDecimal("1400"));
            property4.setYearBuilt(2018);
            property4.setAvailable(true);
            property4.setOwner(agent1);
            property4 = propertyRepository.save(property4);
            
            PropertyImage img4_1 = new PropertyImage();
            img4_1.setImageUrl("https://images.unsplash.com/photo-1545324418-cc1a3fa10c00");
            img4_1.setCaption("Modern condo building");
            img4_1.setIsPrimary(true);
            img4_1.setDisplayOrder(1);
            img4_1.setProperty(property4);
            imageRepository.save(img4_1);
            
            Property property5 = new Property();
            property5.setTitle("Prime Commercial Property");
            property5.setDescription("Excellent commercial space in high-traffic area. Perfect for retail or office use. Ample parking available.");
            property5.setPrice(new BigDecimal("850000"));
            property5.setAddress("555 Business Boulevard");
            property5.setCity("Schaumburg");
            property5.setState("IL");
            property5.setZipCode("60173");
            property5.setPropertyType(PropertyType.COMMERCIAL);
            property5.setListingType(ListingType.FOR_SALE);
            property5.setBedrooms(0);
            property5.setBathrooms(2);
            property5.setSquareFeet(new BigDecimal("3500"));
            property5.setYearBuilt(2012);
            property5.setAvailable(true);
            property5.setOwner(agent2);
            property5 = propertyRepository.save(property5);
            
            PropertyImage img5_1 = new PropertyImage();
            img5_1.setImageUrl("https://images.unsplash.com/photo-1486406146926-c627a92ad1ab");
            img5_1.setCaption("Commercial building");
            img5_1.setIsPrimary(true);
            img5_1.setDisplayOrder(1);
            img5_1.setProperty(property5);
            imageRepository.save(img5_1);
            
            // Create Favorites
            Favorite fav1 = new Favorite();
            fav1.setUser(buyer1);
            fav1.setProperty(property1);
            fav1.setNotes("Love the backyard!");
            favoriteRepository.save(fav1);
            
            Favorite fav2 = new Favorite();
            fav2.setUser(buyer1);
            fav2.setProperty(property3);
            fav2.setNotes("Great location near parks");
            favoriteRepository.save(fav2);
            
            Favorite fav3 = new Favorite();
            fav3.setUser(agent2);
            fav3.setProperty(property2);
            favoriteRepository.save(fav3);
            
            // Create Subscriptions
            Subscription sub1 = new Subscription();
            sub1.setUser(agent1);
            sub1.setPlanType(SubscriptionType.PREMIUM);
            sub1.setStartDate(LocalDate.now().minusMonths(2));
            sub1.setEndDate(LocalDate.now().plusMonths(10));
            sub1.setPrice(new BigDecimal("19.99"));
            sub1.setActive(true);
            sub1.setAutoRenew(true);
            subscriptionRepository.save(sub1);
            
            Subscription sub2 = new Subscription();
            sub2.setUser(buyer1);
            sub2.setPlanType(SubscriptionType.BASIC);
            sub2.setStartDate(LocalDate.now().minusDays(15));
            sub2.setEndDate(LocalDate.now().plusDays(15));
            sub2.setPrice(new BigDecimal("9.99"));
            sub2.setActive(true);
            sub2.setAutoRenew(false);
            subscriptionRepository.save(sub2);
            
            System.out.println("✅ Database initialized with sample data!");
            System.out.println("📊 Created " + userRepository.count() + " users");
            System.out.println("🏠 Created " + propertyRepository.count() + " properties");
            System.out.println("📸 Created " + imageRepository.count() + " property images");
            System.out.println("❤️ Created " + favoriteRepository.count() + " favorites");
            System.out.println("💳 Created " + subscriptionRepository.count() + " subscriptions");
        };
    }
}