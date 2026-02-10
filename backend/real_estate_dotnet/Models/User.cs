using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Models
{
    [Table("users")]
    public class User
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        [Required(ErrorMessage = "First name is required")]
        public string FirstName { get; set; } = string.Empty;

        [Required(ErrorMessage = "Last name is required")]
        public string LastName { get; set; } = string.Empty;

        [Required(ErrorMessage = "Email is required")]
        [EmailAddress(ErrorMessage = "Email should be valid")]
        public string Email { get; set; } = string.Empty;

        [Required(ErrorMessage = "Password is required")]
        [MinLength(6, ErrorMessage = "Password must be at least 6 characters")]
        [JsonIgnore]
        public string Password { get; set; } = string.Empty;

        [Required(ErrorMessage = "Phone is required")]
        public string Phone { get; set; } = string.Empty;

        [Required]
        public UserType UserType { get; set; }

        [Required]
        public Role Role { get; set; } = Role.User;

        public SubscriptionType SubscriptionType { get; set; } = SubscriptionType.Free;

        public string? Company { get; set; }

        public string? LicenseNumber { get; set; }

        [MaxLength(1000)]
        public string? Bio { get; set; }

        public string? ProfileImageUrl { get; set; }

        public bool Active { get; set; } = true;

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

        // One User -> Many Properties
        [JsonIgnore]
        public List<Property> Properties { get; set; } = new();

        // One User -> Many Favorites
        [JsonIgnore]
        public List<Favorite> Favorites { get; set; } = new();

        // One User -> Many Search Histories
        [JsonIgnore]
        public List<SearchHistory> SearchHistories { get; set; } = new();

        // One User -> One Subscription
        [JsonIgnore]
        public Subscription? Subscription { get; set; }

        public string FullName => $"{FirstName} {LastName}";
    }
}
