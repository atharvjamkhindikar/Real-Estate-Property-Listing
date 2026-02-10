using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Models
{
    [Table("subscriptions")]
    public class Subscription
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        // One-to-One: User ↔ Subscription
        [Required]
        public long UserId { get; set; }

        [ForeignKey("UserId")]
        [JsonIgnore]
        public User User { get; set; }

        // Plan type
        [Required]
        [Column(TypeName = "nvarchar(20)")]
        public SubscriptionType PlanType { get; set; } = SubscriptionType.FREE;

        [Required]
        public DateTime StartDate { get; set; } = DateTime.UtcNow.Date;

        public DateTime? EndDate { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? Price { get; set; }

        [Required]
        public bool Active { get; set; } = true;

        [Required]
        public bool AutoRenew { get; set; } = false;

        public string? PaymentMethod { get; set; }

        public string? TransactionId { get; set; }

        [Required]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        [Required]
        public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

        // Equivalent of @PreUpdate
        public void Touch()
        {
            UpdatedAt = DateTime.UtcNow;
        }

        // Business logic
        [NotMapped]
        public bool IsExpired =>
            EndDate.HasValue && DateTime.UtcNow.Date > EndDate.Value.Date;
    }
}
