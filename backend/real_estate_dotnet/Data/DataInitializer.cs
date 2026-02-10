using real_estate_dotnet.Models;

namespace real_estate_dotnet.Data
{
    public static class DataInitializer
    {
        public static void Initialize(ApplicationDbContext context)
        {
            context.Database.EnsureCreated();

            if (context.Properties.Any()) return;

            context.Properties.AddRange(
                new Property { Name = "Sample House 1", Price = 500000 },
                new Property { Name = "Sample Apartment", Price = 300000 }
            );

            context.SaveChanges();
        }
    }
}
