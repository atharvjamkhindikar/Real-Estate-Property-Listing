using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Models
{
    public class Favorite
    {
        [Key]
        public long Id { get; set; }

        [Required]
        public long UserId { get; set; }

        [ForeignKey("UserId")]
        public User User { get; set; }

        [Required]
        public long PropertyId { get; set; }

        [ForeignKey("PropertyId")]
        public Property Property { get; set; }

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    }
}
