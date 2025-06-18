# Quarkus AI Showcase [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

[![CircleCI](https://circleci.com/gh/piomin/quarkus-ai-showcase.svg?style=svg)](https://circleci.com/gh/piomin/quarkus-ai-showcase)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=piomin_quarkus-ai-showcase&metric=bugs)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=piomin_quarkus-ai-showcase&metric=coverage)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=piomin_quarkus-ai-showcase&metric=ncloc)](https://sonarcloud.io/dashboard?id=piomin_quarkus-ai-showcase)

A comprehensive Quarkus application demonstrating AI integration using LangChain4j with multiple AI providers: **OpenAI**, **Mistral AI**, and **Ollama**. The application showcases person data generation and retrieval with chat memory capabilities.

## Articles

1. Getting started with Quarkus LangChain4j **Chat Model** and easily switch between different AI providers including **OpenAI**, **Mistral AI** and **Ollama**. The example is available in the branch [master](https://github.com/piomin/quarkus-ai-showcase/tree/master). A detailed guide may be found in the following article: [Getting Started with Quarkus LangChain4j and Chat Model](https://piotrminkowski.com/2025/06/18/getting-started-with-quarkus-langchain4j-and-chat-model/)

## Features

- 🤖 **Multi-AI Provider Support**: Switch between OpenAI, Mistral AI, and Ollama
- 🧠 **Chat Memory**: Maintains conversation context for personalized interactions
- 🚀 **RESTful API**: Clean REST endpoints for AI-powered person generation
- ⚡ **Quarkus Performance**: Lightning-fast startup and low memory footprint
- 🔧 **Easy Configuration**: Simple environment-based configuration

## Prerequisites

- Java 17 or higher
- Maven 3.8+ or Gradle
- Choose at least one AI provider:

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

### 3. Run the Application

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

## Technology Stack

- **Quarkus 3.15.1** - Supersonic Subatomic Java Framework
- **LangChain4j** - Java library for building AI applications
  - OpenAI integration (v0.26.2)
  - Mistral AI integration (v0.27.0.CR1)
  - Ollama integration (v0.26.0.CR2)
- **JAX-RS (Quarkus REST)** - RESTful web services
- **Jackson** - JSON processing
- **CDI (Contexts and Dependency Injection)** - Dependency injection

## Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   REST Client   │───▶│ PersonController │───▶│ PersonAiService │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                                     │
                   ┌──────────────────┐            │
                   │   Config Class   │◄───────────┘
                   │  (Provider Setup)│
                   └──────────────────┘
                            │
                   ┌──────────────────┐
                   │   LangChain4j    │
                   │   AI Providers   │
                   └──────────────────┘
                            │
         ┌──────────────────┼──────────────────┐
         │                  │                  │
 ┌───────▼────────┐ ┌───────▼────────┐ ┌──────▼───────┐
 │     OpenAI     │ │   Mistral AI   │ │    Ollama    │
 │   (gpt-4o-mini)│ │ (mistral-small)│ │   (llama3.2) │
 └────────────────┘ └────────────────┘ └──────────────┘
```

## Development

### Project Structure
```
src/main/java/com/example/
├── PersonController.java          # REST endpoints
├── Config.java                    # AI provider configuration
├── model/
│   └── Person.java                # Person data model
└── service/
    └── PersonAiService.java       # AI service interface
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

**General Debug Steps:**
1. Enable debug logging by setting `%dev.quarkus.log.console.level=DEBUG`
2. Check application logs for specific error messages
3. Verify environment variables are set: `echo $AI_MODEL_PROVIDER`
4. Test API endpoints with different AI providers

### Logging

The application logs detailed information about:
- Selected AI provider on startup
- API requests and responses
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