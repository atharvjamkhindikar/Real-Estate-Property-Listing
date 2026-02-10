using Microsoft.Extensions.DependencyInjection;

namespace real_estate_dotnet.Config
{
    public static class HttpClientConfig
    {
        public static void AddHttpClientConfig(this IServiceCollection services)
        {
            services.AddHttpClient(); // registers IHttpClientFactory
        }
    }
}
