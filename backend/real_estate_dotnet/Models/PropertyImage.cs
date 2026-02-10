using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Models
{
    [Table("property_images")]
    public class PropertyImage
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }

        [Required]
        public string ImageUrl { get; set; }

        [MaxLength(500)]
        public string? Caption { get; set; }

        public bool IsPrimary { get; set; } = false;

        public int DisplayOrder { get; set; } = 0;

        public DateTime UploadedAt { get; set; } = DateTime.UtcNow;

        // Many images → One property
        [Required]
        public long PropertyId { get; set; }

        [JsonIgnore]
        public Property Property { get; set; }
    }
}
