using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;



namespace real_estate_dotnet.Models
{
    [Table("properties")]
    public class Property
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        [Required(ErrorMessage = "Title is required")]
        public string Title { get; set; }

        [Required(ErrorMessage = "Description is required")]
        [MaxLength(2000)]
        public string Description { get; set; }

        [Required(ErrorMessage = "Price is required")]
        [Range(0.01, double.MaxValue, ErrorMessage = "Price must be greater than 0")]
        public decimal Price { get; set; }

        [Required(ErrorMessage = "Address is required")]
        public string Address { get; set; }

        [Required(ErrorMessage = "City is required")]
        public string City { get; set; }

        [Required(ErrorMessage = "State is required")]
        public string State { get; set; }

        [Required(ErrorMessage = "Zip code is required")]
        public string ZipCode { get; set; }

        [Required(ErrorMessage = "Property type is required")]
        public PropertyType PropertyType { get; set; }

        [Required(ErrorMessage = "Listing type is required")]
        public ListingType ListingType { get; set; }

        [Range(0, int.MaxValue)]
        public int? Bedrooms { get; set; }

        [Range(0, int.MaxValue)]
        public int? Bathrooms { get; set; }

        [Range(0, double.MaxValue)]
        public decimal? SquareFeet { get; set; }

        public int? YearBuilt { get; set; }

        // Backward compatibility
        public string? ImageUrl { get; set; }

        public bool Available { get; set; } = true;

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

        public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

        // Many properties → One owner (User)
        public long? OwnerId { get; set; }

        [JsonIgnore]
        public User? Owner { get; set; }

        // Many properties → One builder group
        public long? BuilderGroupId { get; set; }

        [JsonIgnore]
        public BuilderGroup? BuilderGroup { get; set; }

        // One property → Many images
        public List<PropertyImage> Images { get; set; } = new();
    }
}
