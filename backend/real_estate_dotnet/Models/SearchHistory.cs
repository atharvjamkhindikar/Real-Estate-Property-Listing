using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace RealEstate.Model
{
    [Table("search_history")]
    [Index(nameof(UserId), Name = "idx_search_user")]
    [Index(nameof(SearchedAt), Name = "idx_search_timestamp")]
    public class SearchHistory
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        // User who performed the search
        [Required]
        public long UserId { get; set; }

        [ForeignKey("UserId")]
        [JsonIgnore]
        public User User { get; set; }

        // Search criteria (individual fields for analytics)
        public string? SearchCity { get; set; }
        public string? SearchState { get; set; }

        [Column(TypeName = "nvarchar(20)")]
        public PropertyType? SearchPropertyType { get; set; }

        [Column(TypeName = "nvarchar(20)")]
        public ListingType? SearchListingType { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? MinPrice { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? MaxPrice { get; set; }

        public int? MinBedrooms { get; set; }
        public int? MaxBedrooms { get; set; }

        public int? MinBathrooms { get; set; }
        public int? MaxBathrooms { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? MinSquareFeet { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? MaxSquareFeet { get; set; }

        // Full search criteria as JSON (for complex queries)
        [MaxLength(2000)]
        public string? SearchCriteria { get; set; }

        public int? ResultsCount { get; set; }

        // Timestamp
        [Required]
        public DateTime SearchedAt { get; set; } = DateTime.UtcNow;
    }
}
