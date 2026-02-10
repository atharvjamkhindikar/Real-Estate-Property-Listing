using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        // Tables
        public DbSet<User> Users { get; set; }
        public DbSet<Property> Properties { get; set; }
        public DbSet<Subscription> Subscriptions { get; set; }
        public DbSet<Favorite> Favorites { get; set; }
        public DbSet<PropertyImage> PropertyImages { get; set; }
        public DbSet<ScheduleViewing> ScheduleViewings { get; set; }
        public DbSet<SearchHistory> SearchHistories { get; set; }
        public DbSet<ContactAgent> ContactAgents { get; set; }
        public DbSet<BuilderGroup> BuilderGroups { get; set; }
    }
}
