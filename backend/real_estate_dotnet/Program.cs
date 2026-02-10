using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;

var builder = WebApplication.CreateBuilder(args);

// -----------------------------
// 1️ Add Services
// -----------------------------
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Add DbContext BEFORE building the app
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection"))
);

// -----------------------------
// 2️ Build the App
// -----------------------------
var app = builder.Build();

// -----------------------------
// 3️Middleware Pipeline
// -----------------------------
app.UseSwagger();
app.UseSwaggerUI();

app.UseHttpsRedirection(); // optional but recommended

app.UseAuthorization(); // if you add auth later

// Map controllers
app.MapControllers();

// -----------------------------
// 4️ Run the App
// -----------------------------
app.Run();
