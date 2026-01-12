AGENTS GUIDE FOR THIS REPOSITORY

Audience: agentic coding agents (Cursor, Copilot, Jarvis-style) working in this repo. The goal is CONSISTENCY and RELIABILITY. Concepts first, commands second. Don’t guess.

Repository overview
- Language: Java 17
- Build tool: Maven
- Test framework: JUnit 5 (Jupiter)
- Packaging: jar
- IDE metadata: Eclipse files present (.project, .classpath)
- No Spring. No Lombok. Keep it simple.

Golden rules
1) Business logic lives in plain Java classes. No framework magic.
2) Tests MUST be deterministic and fast. No sleeps. No randomness.
3) Error handling: prefer explicit exceptions with clear messages.
4) If it’s hard to test, the design is wrong. Refactor.
5) Concurrency awareness: even if not used now, avoid shared mutable state.

Build, test, and run commands
- Clean and build:
  - mvn -q clean package
- Compile only:
  - mvn -q compile
- Run all tests:
  - mvn -q -DskipTests=false test
- Run a single test class (JUnit 5 via Surefire 3.3.0):
  - mvn -q -Dtest=com.javafoundations.app.semana1.CalculatorTest test
- Run a single test method:
  - mvn -q -Dtest=com.javafoundations.app.semana1.CalculatorTest#addsTwoNumbers test
- Run multiple tests (CSV):
  - mvn -q -Dtest=com.javafoundations.app.semana1.BoxTest,com.javafoundations.app.semana1.NumberBoxTest test
- Fuzzy match with patterns:
  - mvn -q -Dtest=*BoxTest test
- Generate jar:
  - mvn -q package
- Install to local repo:
  - mvn -q install

Linting and static checks
- There is NO Checkstyle/SpotBugs configured. Agents should:
  - Keep code warnings-free under javac (Java 17).
  - Avoid unused imports, raw types, and unchecked casts.
  - Prefer final for fields that never change.
  - Consider proposing Checkstyle/SpotBugs if code quality drifts, but DO NOT add plugins without explicit instruction.

Project configuration notes (from pom.xml)
- maven.compiler.release=17 (targets Java 17)
- JUnit BOM 5.11.0
- surefire-plugin 3.3.0 (JUnit 5 compatible)
- Standard Maven lifecycles and plugins pinned

Directory structure
- src/main/java/... production code
- src/test/java/... tests (JUnit 5)
- target/... build outputs
- .mvn/jvm.config and maven.config exist; respect them

Test writing guidelines (JUnit 5)
- Use org.junit.jupiter.api.*
- Prefer @Test over @ParameterizedTest unless needed.
- Name tests with behavior intent:
  - methodName_condition_expectedOutcome
  - Example: addsTwoNumbers_returnsSum
- Keep tests isolated. No shared mutable state. No static state leaks.
- Assertions: use assertEquals, assertThrows, assertTrue with clear messages.
- Avoid broad try/catch in tests; use assertThrows for exceptions.
- One logical behavior per test. Small and focused.

Imports policy
- Use explicit imports. NO wildcard imports (e.g., import java.util.*) unless package has 5+ types used repeatedly.
- Order imports:
  1) java.*
  2) javax.* (not expected here)
  3) third-party (org.junit.jupiter.*)
  4) project packages (com.javafoundations.*)
- Group by category, no blank line spam. Single blank line between groups.
- Remove unused imports immediately.

Formatting rules
- Indentation: 2 spaces OR 4 spaces? Choose 4 spaces for Java (default). Be consistent.
- Line length: target 100 chars max. Break gracefully.
- Braces: K&R style
  - class Foo {
      void bar() {
      }
    }
- One top-level type per file.
- One public class per file; name matches file.
- Use final for parameters and locals when it clarifies intent (optional).
- No trailing whitespace. End files with a newline.

Types and generics
- Avoid raw types entirely.
- Prefer interfaces for field types (e.g., List over ArrayList).
- Use Optional ONLY for return values when absence is expected; do not use Optional for fields or params.
- Avoid nulls in collections; represent absence with empty collections.

Naming conventions
- Packages: all lowercase, no underscores. Example: com.javafoundations.app.semana1
- Classes: PascalCase. Example: NumberBox
- Interfaces: PascalCase without “I” prefix. Example: Repository
- Methods and fields: camelCase
- Constants: UPPER_SNAKE_CASE with final static
- Tests: ClassNameTest for test classes. Methods state behavior.

Error handling
- Throw specific exceptions. Example: NotFoundException for missing elements.
- Messages MUST be actionable. Include identifiers/keys.
- DO NOT swallow exceptions. Either handle or propagate.
- Avoid checked exceptions unless recovery is expected by caller.
- Validate inputs early; fail fast.

Collections and immutability
- Prefer immutable views or defensive copies when exposing collections.
- Avoid returning internal mutable structures.
- Prefer Map/List over arrays unless performance-critical.

Logging
- No logging setup present. If you add logging:
  - Use java.util.logging or slf4j; DO NOT hardcode System.out.println in production code.
  - Keep test logs minimal.

Concurrency basics
- No concurrent code currently, but design for safety:
  - Avoid shared mutable state across threads.
  - Make fields final where possible.
  - If you introduce concurrency, document thread ownership and visibility.

Maven specifics for agents
- Respect existing plugin versions; don’t upgrade without explicit request.
- For single-test execution, use -Dtest=Class#method pattern. This repo’s Surefire supports it.
- Use -q for quiet CI-like output unless user requests verbose.

Coding style examples
- Exception creation:
  - throw new NotFoundException("Box id=" + id + " not found");
- Defensive copy on getters:
  - public List<Item> getItems() { return List.copyOf(items); }
- Guard clauses:
  - if (value < 0) throw new IllegalArgumentException("value must be >= 0");

Refactoring guidance
- If a class does too many things, split by responsibility.
- Tests should drive design. When tests are awkward, simplify the API.
- Prefer composition over inheritance.

CI/CD expectations
- None configured here. If adding CI, run mvn -q -DskipTests=false test on PRs. Cache ~/.m2.

IDE/editor expectations
- Eclipse metadata exists; keep project files intact unless asked to change.
- If adding formatter config, share it in the repo. Until then, follow rules above.

Cursor/Copilot rules
- No Cursor rules (.cursor/rules/, .cursorrules) found.
- No Copilot instructions (.github/copilot-instructions.md) found.
- Therefore, this AGENTS.md is the canonical source of agent guidance.

Do not add dependencies lightly
- Keep dependency graph minimal.
- If you propose adding libraries (e.g., AssertJ, Checkstyle), explain trade-offs and get approval.

Versioning and compatibility
- Target Java 17. Do not introduce APIs requiring >17.
- Tests and code must compile with the current pom.

How to propose changes as an agent
- Create small PRs focused on one improvement.
- Include reasoning in commit message (the WHY). Example: "Refactor NumberBox to avoid shared mutable state and simplify testing."

Final reminder
- Concepts over code. Keep design explainable.
- Transactions aren’t here, but the same discipline applies: know start/end, failure behavior.
- Make it easy to test. Make it easy to read. Make it robust.
