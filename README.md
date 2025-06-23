# Quarkus AI Showcase [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

[![CircleCI](https://circleci.com/gh/piomin/quarkus-ai-showcase.svg?style=svg)](https://circleci.com/gh/piomin/quarkus-ai-showcase)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=piomin_quarkus-ai-showcase&metric=bugs)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=piomin_quarkus-ai-showcase&metric=coverage)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=piomin_quarkus-ai-showcase&metric=ncloc)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)

A comprehensive Quarkus application demonstrating AI integration using LangChain4j with multiple AI providers: **OpenAI**, **Mistral AI**, and **Ollama**. The application showcases person data generation, chat memory capabilities, and **AI-powered stock portfolio management** using LangChain4j Tools.

## Articles

1. Getting started with Quarkus LangChain4j **Chat Model** and easily switch between different AI providers including **OpenAI**, **Mistral AI** and **Ollama**. The example is available in the branch [master](https://github.com/piomin/quarkus-ai-showcase/tree/master). A detailed guide may be found in the following article: [Getting Started with Quarkus LangChain4j and Chat Model](https://piotrminkowski.com/2025/06/18/getting-started-with-quarkus-langchain4j-and-chat-model/)
2.  AI **Tool Calling** with Quarkus LangChain4j. How to call AI functions with Quarkus, OpenAI, and LangChain4j. The example is available in the branch [master](https://github.com/piomin/quarkus-ai-showcase/tree/master). A detailed guide may be found in the following article: [AI Tool Calling with Quarkus LangChain4j](https://piotrminkowski.com/2025/06/23/ai-tool-calling-with-quarkus-langchain4j/)

## Features

- 🤖 **Multi-AI Provider Support**: Switch between OpenAI, Mistral AI, and Ollama
- 🧠 **Chat Memory**: Maintains conversation context for personalized interactions
- 🚀 **RESTful API**: Clean REST endpoints for AI-powered person generation
- 🛠️ **LangChain4j Tools**: AI agents with access to external APIs and database operations
- 💼 **Smart Portfolio Management**: AI-powered stock wallet analysis and calculations
- 📊 **Real-time Stock Data**: Integration with external stock market APIs
- ⚡ **Quarkus Performance**: Lightning-fast startup and low memory footprint
- 🔧 **Easy Configuration**: Simple environment-based configuration

## Prerequisites

- Java 17 or higher
- Maven 3.8+ or Gradle
- Choose at least one AI provider:
- Stock API key (optional, for wallet functionality)

### OpenAI Setup
1. Create an account at [OpenAI](https://platform.openai.com/)
2. Generate an API key from the API keys section
3. Set the `OPEN_AI_TOKEN` environment variable

### Mistral AI Setup
1. Create an account at [Mistral AI](https://console.mistral.ai/)
2. Generate an API key from your dashboard
3. Set the `MISTRAL_AI_TOKEN` environment variable

### Ollama Setup
1. Install Ollama from [ollama.ai](https://ollama.ai/)
2. Start the Ollama service: `ollama serve`
3. Pull a model: `ollama pull llama2` (or any compatible model)
4. Ensure Ollama is running on `http://localhost:11434` (default)

### Stock API Setup (Optional)
For wallet functionality with real-time stock data:
1. Create an account at [Twelve Data](https://twelvedata.com/)
2. Generate a free API key from your dashboard
3. Set the `STOCK_API_KEY` environment variable

**Note:** The wallet functionality will work without the API key, but will use mock data for demonstration purposes.

## Configuration

### AI Provider Selection

Set the AI provider using the `AI_MODEL_PROVIDER` environment variable:

```bash
# For OpenAI (default)
export AI_MODEL_PROVIDER=openai
export OPEN_AI_TOKEN=your-openai-api-key

# For Mistral AI
export AI_MODEL_PROVIDER=mistral
export MISTRAL_AI_TOKEN=your-mistral-api-key

# For Ollama
export AI_MODEL_PROVIDER=ollama
```

### Stock API Configuration

```bash
# Optional: For real stock data (Twelve Data API)
export STOCK_API_KEY=your-twelvedata-api-key
```

### Model Configuration

Each provider has configurable models and parameters in `src/main/resources/application.properties`:

**OpenAI Models:**
- `gpt-4o-mini` (default)
- `gpt-4`
- `gpt-4-turbo`

**Mistral AI Models:**
- `mistral-tiny` (default)
- `mistral-small`
- `mistral-medium`
- `mistral-large`

**Ollama Models:**
- `llama3.2` (default)
- `codellama`
- `mistral`
- Any other model you have pulled

## Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd quarkus-ai-showcase
```

### 2. Configure Your AI Provider

**Option A: Using OpenAI**
```bash
export AI_MODEL_PROVIDER=openai
export OPEN_AI_TOKEN=your-actual-api-key
```

**Option B: Using Mistral AI**
```bash
export AI_MODEL_PROVIDER=mistral
export MISTRAL_AI_TOKEN=your-actual-api-key
```

**Option C: Using Ollama**
```bash
# Start Ollama first
ollama serve

# In another terminal, pull a model
ollama pull llama2

# Set environment
export AI_MODEL_PROVIDER=ollama
```

### 3. Configure Stock API (Optional)
```bash
# For real-time stock data in wallet functionality
export STOCK_API_KEY=your-twelvedata-api-key
```

### 4. Run the Application

**Development Mode (with hot reload):**
```bash
./mvnw quarkus:dev
```

**Production Mode:**
```bash
./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
```

**Native Build (optional):**
```bash
./mvnw clean package -Pnative
./target/quarkus-ai-showcase-1.0.0-SNAPSHOT-runner
```

The application will start on `http://localhost:8080`

## API Endpoints

### Person Generation Endpoints

### Generate Persons
**Endpoint:** `GET /api/persons/generate`

Generates a list of 10 random persons using AI.

**Example Request:**
```bash
curl -X GET http://localhost:8080/api/persons
```

**Example Response:**
```json
[
  {
    "id": "1",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "age": 30,
    "city": "New York"
  },
  {
    "id": "2",
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@email.com",
    "age": 25,
    "city": "Los Angeles"
  }
]
```

### Get Person by ID
**Endpoint:** `GET /api/persons/{id}`

Retrieves a specific person by ID using AI with chat memory capabilities.

**Example Request:**
```bash
curl -X GET http://localhost:8080/api/persons/1
```

**Example Response:**
```json
{
  "id": "1",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "age": 30,
  "city": "New York"
}
```

### Wallet and Stock Portfolio Endpoints

### Calculate Current Wallet Value
**Endpoint:** `GET /wallet/with-tools`

Uses AI with LangChain4j tools to calculate the current value of your stock portfolio based on real-time stock prices.

**Example Request:**
```bash
curl -X GET http://localhost:8080/wallet/with-tools
```

**Example Response:**
```
Based on the current stock prices, your wallet value is approximately $15,420.50. 
Here's the breakdown:
- AAPL: 10 shares at $154.25 = $1,542.50
- GOOGL: 5 shares at $125.60 = $628.00
- TSLA: 8 shares at $201.20 = $1,609.60
- MSFT: 12 shares at $284.91 = $3,418.92
- AMZN: 15 shares at $145.86 = $2,187.90
- NVDA: 6 shares at $875.28 = $5,251.68
- META: 4 shares at $245.64 = $982.56
```

### Find Highest Portfolio Value Day
**Endpoint:** `GET /wallet/highest-day/{days}`

Uses AI to analyze historical stock data and determine on which day during the specified period your portfolio had the highest value.

**Example Request:**
```bash
curl -X GET http://localhost:8080/wallet/highest-day/30
```

**Example Response:**
```
During the last 30 days, your wallet had the highest value on January 15, 2024, 
with a total value of $16,847.32. This was primarily due to strong performance 
in NVDA (+12.5%) and TSLA (+8.3%) on that day.
```

## Technology Stack

- **Quarkus 3.15.1** - Supersonic Subatomic Java Framework
- **LangChain4j** - Java library for building AI applications
  - OpenAI integration (v0.26.2)
  - Mistral AI integration (v0.27.0.CR1)
  - Ollama integration (v0.26.0.CR2)
  - AI Tools and Agents support
- **JAX-RS (Quarkus REST)** - RESTful web services
- **Hibernate ORM with Panache** - Database operations
- **REST Client** - External API integration
- **Jackson** - JSON processing
- **CDI (Contexts and Dependency Injection)** - Dependency injection

## Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   REST Client   │───▶│ PersonController │───▶│ PersonAiService │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                       ┌──────────────────┐    ┌─────────────────┐
                       │ WalletController │───▶│ WalletAiService │
                       └──────────────────┘    └─────────────────┘
                                                     │
                                        ┌────────────┼────────────┐
                                        │            │            │
                                   ┌────▼────┐ ┌────▼────┐ ┌─────▼──────┐
                                   │  Stock  │ │  Share  │ │   Config   │
                                   │  Tools  │ │  Tools  │ │   Class    │
                                   └────┬────┘ └────┬────┘ └─────┬──────┘
                                        │           │            │
                              ┌─────────▼───┐   ┌───▼────────┐   │
                              │ Stock Data  │   │   Share    │   │
                              │   Client    │   │Repository  │   │
                              └─────────────┘   └────────────┘   │
                                        │                        │
                                   ┌────▼────┐                   │
                                   │External │                   │
                                   │Stock API│                   │
                                   └─────────┘                   │
                                                                 │
                                        ┌────────────────────────▼────┐
                                        │         LangChain4j         │
                                        │        AI Providers         │
                                        └────────────────────────────┘  
                                                     │
                                  ┌──────────────────┼──────────────────┐
                                  │                  │                  │
                          ┌───────▼────────┐ ┌───────▼────────┐ ┌──────▼───────┐
                          │     OpenAI     │ │   Mistral AI   │ │    Ollama    │
                          │   (gpt-4o-mini)│ │ (mistral-small)│ │   (llama3.2) │
                          └────────────────┘ └────────────────┘ └──────────────┘
```

## LangChain4j AI Tools

The application demonstrates advanced AI capabilities using LangChain4j Tools, which allow AI models to interact with external systems and databases:

### Stock Tools
- **Latest Stock Prices**: Retrieves real-time stock prices for companies
- **Historical Stock Data**: Fetches historical stock prices for trend analysis
- **External API Integration**: Connects to Twelve Data API for market data

### Share Tools  
- **Portfolio Information**: Accesses user's stock holdings from the database
- **Database Integration**: Uses Hibernate Panache for data operations

### AI Service Integration
The `WalletAiService` combines both tool sets to provide intelligent portfolio analysis:
```java
@RegisterAiService(tools = {StockTools.class, ShareTools.class})
public interface WalletAiService {
    // AI can automatically call tools to calculate portfolio values
    String calculateWalletValueWithTools();
    
    // AI can analyze historical data to find optimal periods
    String calculateHighestWalletValue(int days);
}
```

## Development

### Project Structure
```
src/main/java/com/example/
├── PersonController.java          # REST endpoints
├── WalletController.java          # Wallet/portfolio endpoints
├── Config.java                    # AI provider configuration
├── model/
│   ├── Person.java                # Person data model
│   └── Share.java                 # Stock share entity
├── repository/
│   └── ShareRepository.java       # Database operations for shares
├── service/
│   ├── PersonAiService.java       # AI service interface
│   └── WalletAiService.java       # AI service with tools
├── tools/
│   ├── StockTools.java            # Stock market data tools
│   └── ShareTools.java            # Portfolio data tools
└── api/
    ├── external/                  # External API models
    └── client/                    # REST clients
```

### Running Tests
```bash
./mvnw test
```

### Hot Reload Development
```bash
./mvnw quarkus:dev -P(open-ai|mistral-ai|ollama)
```
This enables hot reload—changes to your code will be automatically reflected without restarting the application.

## Troubleshooting

### Common Issues

**OpenAI API Key Issues:**
- Ensure your API key is valid and has sufficient credits
- Check that the `OPEN_AI_TOKEN` environment variable is set correctly
- Verify your OpenAI account has access to the specified model

**Mistral AI Connection Issues:**
- Verify your `MISTRAL_AI_TOKEN` is correctly set
- Check your Mistral AI account status and quota
- Ensure the model name is correct in the configuration

**Ollama Connection Issues:**
- Ensure Ollama is running: `ollama serve`
- Check if the model is pulled: `ollama list`
- Verify the base URL is accessible: `curl http://localhost:11434/api/version`
- Try pulling the model: `ollama pull llama2`

**Stock API Issues:**
- Verify your `STOCK_API_KEY` is set correctly
- Check your Twelve Data API quota and limits
- Test the API endpoint directly: `curl "https://api.twelvedata.com/time_series?symbol=AAPL&interval=1min&apikey=YOUR_KEY"`
- The application will work with mock data if no API key is provided

**General Debug Steps:**
1. Enable debug logging by setting `%dev.quarkus.log.console.level=DEBUG`
2. Check application logs for specific error messages
3. Verify environment variables are set: `echo $AI_MODEL_PROVIDER`
4. Test API endpoints with different AI providers

### Logging

The application logs detailed information about:
- Selected AI provider on startup
- API requests and responses
- Stock API calls and responses
- AI tool executions and results
- Error details for troubleshooting

## Contributing

1. Fork the repository  
2. Create a feature branch  
3. Make your changes  
4. Add tests if applicable  
5. Submit a pull request  

## License

This project is open source and available under the [MIT License](LICENSE).

## Support

For questions and support:
- Check the [troubleshooting section](#troubleshooting)
- Review the application logs
- Open an issue on GitHub

---

**Happy coding with AI! 🚀🤖**