using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace RealEstate.Model
{
    [Table("schedule_viewings")]
    public class ScheduleViewing
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        // User who wants to view the property
        [Required]
        public long UserId { get; set; }

        [ForeignKey("UserId")]
        [JsonIgnore]
        public User User { get; set; }

        // Property to be viewed
        [Required]
        public long PropertyId { get; set; }

        [ForeignKey("PropertyId")]
        [JsonIgnore]
        public Property Property { get; set; }

        // Viewing date (only date)
        [Required]
        public DateOnly ViewingDate { get; set; }

        // Viewing time (only time)
        [Required]
        public TimeOnly ViewingTime { get; set; }

        // Viewing status
        [Required]
        [Column(TypeName = "nvarchar(20)")]
        public ViewingStatus Status { get; set; } = ViewingStatus.Pending;

        // Optional notes
        [MaxLength(1000)]
        public string? Notes { get; set; }

        // Rejection reason
        [MaxLength(500)]
        public string? RejectionReason { get; set; }

        // Timestamps
        [Required]
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        public DateTime? ConfirmedAt { get; set; }
        public DateTime? RejectedAt { get; set; }
        public DateTime? CompletedAt { get; set; }
        public DateTime? CancelledAt { get; set; }
    }
}
