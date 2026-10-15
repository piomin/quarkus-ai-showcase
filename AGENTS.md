### AI Agents Best Practices for Code Generation

This document outlines best practices for generating code within this repository using AI agents.

#### 1. Project Overview
*   **Stack:** Quarkus 3.x application with Quarkus LangChain4j integration, built with Maven and Java 21.
*   **Architecture:** Single-module application (not microservices) showcasing AI/LLM capabilities via `quarkus-langchain4j`.
*   **Key patterns used:**
    *   AI Services (`@RegisterAiService`) — declarative LLM integration interfaces in `pl.piomin.quarkus.ai.service`
    *   Tools (`@Tool`) — methods callable by the LLM at runtime, in `pl.piomin.quarkus.ai.tools`
    *   Output Guardrails (`@OutputGuardrails`) — validation of LLM responses, in `pl.piomin.quarkus.ai.validate`
    *   Chat Memory (`@MemoryId`) — per-user conversation memory
    *   REST endpoints via JAX-RS (`@Path`) in `pl.piomin.quarkus.ai.resources`
    *   Hibernate ORM Panache with H2 for persistence
    *   MicroProfile REST Client for external API calls (Twelve Data stock API)

#### 2. Maven Profiles
The project supports multiple LLM providers via Maven profiles:
*   `open-ai` (default, active by default) — OpenAI integration
*   `mistral-ai` — Mistral AI integration
*   `ollama` — Ollama (local LLM) integration

Activate a profile with: `mvn quarkus:dev -Pmistral-ai`

#### 3. Code Generation Guidelines
*   **Be Specific:** Instead of "add a new endpoint," use "add a GET endpoint to `WalletController` that returns the wallet history."
*   **Reference Existing Patterns:** Follow the patterns in `PersonAiService` / `WalletAiService` for new AI services, and `ShareTools` / `StockTools` for new tools.
*   **Iterative Development:** For complex features, break down the request into smaller steps (e.g., model -> repository -> tool -> AI service -> controller).
*   **Request Tests:** Always generate corresponding tests using JUnit 5, `@QuarkusTest`, and REST Assured (see `PersonControllerTest` for reference).

#### 4. Code Quality and Style
*   **Maintain Consistency:** Follow existing Quarkus and CDI conventions (constructor injection, `@ApplicationScoped`, JAX-RS annotations).
*   **Java 21:** Use Java 21 features where appropriate (records, text blocks, pattern matching).
*   **Review Generated Code:** Always review AI-generated code for security vulnerabilities, performance issues, and adherence to project-specific rules.

#### 5. Critical Restriction: Target Directory
*   **Do Not Commit `target/`:** Under no circumstances should the `target` directory (or any of its contents) be added or committed to the repository.

#### 6. Build Verification
*   **Compile check:** After applying changes, run `mvn clean compile` to ensure the project builds correctly.
*   **Dev mode:** Use `mvn quarkus:dev` for live-reload development.
*   **Run tests:** Use `mvn test` to verify tests pass. Tests require LLM provider configuration (API keys via environment variables).
