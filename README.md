# TicTacToe - DevSecOps CI/CD

Java 19 Maven console TicTacToe with a CI/CD pipeline: Checkstyle, CodeQL, OWASP, tests, Docker build, Trivy scan, smoke test, DockerHub push; CD deploys to Kubernetes (kind) using declarative YAML.

---

## Architecture

**CI (ci.yml):** Checkstyle → CodeQL (SAST) → OWASP (SCA) → Unit tests → Build JAR → Docker build → Trivy scan → Smoke test → Push to DockerHub

**CD (cd.yml):** workflow_run after CI success, or workflow_dispatch. Creates kind cluster, builds/loads image, deploys `k8s/deployment.yaml` and `k8s/service.yaml`, waits for rollout, smoke test in pod.

**Security:** Checkstyle, CodeQL, OWASP, Trivy (fail on CRITICAL/HIGH), non-root container, K8s securityContext. Shift-left: quality and security checks before build and deployment.

---

## Run Locally

**Prerequisites:** Java 19, Maven 3.9+, Docker, kubectl, kind

```bash
# Build and run
mvn clean package
java -jar target/tictactoe-app.jar
java -jar target/tictactoe-app.jar --smoke-test

# Tests and checks
mvn test
mvn checkstyle:check

# Docker
docker build -t tictactoe-app:latest .
docker run --rm tictactoe-app:latest --smoke-test

# Kubernetes (kind)
kind create cluster --name tictactoe-cluster
kubectl config use-context kind-tictactoe-cluster
# Set IMAGE_PLACEHOLDER in k8s/deployment.yaml to tictactoe-app:latest, then:
kubectl apply -f k8s/deployment.yaml -n tictactoe
kubectl apply -f k8s/service.yaml -n tictactoe
kind load docker-image tictactoe-app:latest --name tictactoe-cluster
kubectl get pods -n tictactoe
kind delete cluster --name tictactoe-cluster
```

---

## GitHub Secrets

- `DOCKERHUB_USERNAME` – DockerHub login
- `DOCKERHUB_TOKEN` – DockerHub token (Settings → Security)
- `NVD_API_KEY` (optional) – for OWASP; get at nvd.nist.gov

---

## App Modes

- `java -jar app.jar` – interactive game
- `java -jar app.jar --smoke-test` – automated game, prints APP_OK on success
- `java -jar app.jar --server` – long-running for K8s (used in deployment)

---

## References

- [GitHub Actions](https://docs.github.com/en/actions)
- [CodeQL](https://codeql.github.com/docs/)
- [OWASP Dependency Check](https://owasp.org/www-project-dependency-check/)
- [Trivy](https://aquasecurity.github.io/trivy/)
- [Kubernetes](https://kubernetes.io/docs/)
