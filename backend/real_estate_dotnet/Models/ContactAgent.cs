using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Models
{
    [Table("contact_agents")]
    public class ContactAgent
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        // The user contacting the agent
        [Required]
        public long UserId { get; set; }

        [JsonIgnore]
        public User User { get; set; }

        // The property being enquired about
        [Required]
        public long PropertyId { get; set; }

        [JsonIgnore]
        public Property Property { get; set; }

        [Required]
        public string Subject { get; set; }

        [Required]
        [MaxLength(2000)]
        public string Message { get; set; }

        [Required]
        public string SenderName { get; set; }

        [Required]
        [EmailAddress]
        public string SenderEmail { get; set; }

        [Required]
        public string SenderPhone { get; set; }

        [Column(TypeName = "text")]
        public string? AdditionalInfo { get; set; }

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        public DateTime? RespondedAt { get; set; }

        public bool IsRead { get; set; } = false;
    }
}
