# Prompt: Generate a Detailed Design Description (DESIGN.md)

Paste the prompt below (adjust the bracketed bits) to have an AI coding assistant produce a
`DESIGN.md` for any codebase, to the same standard as this repo's `docs/DESIGN.md`. It's written
to be pasted as-is into a fresh session with full codebase access (e.g. Claude Code).

---

## The prompt

> You are a senior software architect. Produce a detailed design description for this codebase,
> written for an engineer who needs to understand not just *what* the system does but *why* it's
> built the way it is — the kind of document that answers "I know it does X, but when exactly does
> X happen, and what triggers it?" for every major subsystem, not just a restated file tree.
>
> **Before writing anything, research thoroughly and verify every claim against the actual source
> — do not summarize from memory, a README, or partial reads.**
>
> 1. **Map the system in parallel, then verify serially.** Spin up parallel research passes (or
>    work through methodically if not using subagents) covering, at minimum:
>    - The domain/data model — every core entity/table/type, its fields, and its relationships.
>    - The interface surface — every API endpoint / RPC method / CLI command / public function,
>      grouped by module, with auth/permission requirements and one-line purpose each.
>    - Security, config, and infrastructure — auth model, secrets/env handling, rate limiting,
>      deployment topology, environment-specific config.
>    - Whatever is *distinctive* about this codebase — the part a generic description would get
>      wrong. Look for: background workers/queues, event systems, caching layers, retry/outbox
>      patterns, or any recent architectural change (check recent commits/branch names/open PRs)
>      that older docs might not reflect yet.
>    Then go back and **read the actual source files** for anything a research pass only
>    summarized — confirm exact field names, exact status codes, exact trigger conditions. Do not
>    let an unverified summary become a documented "fact."
> 2. **Cross-check existing docs for drift.** If there's a README, an existing design doc, or
>    agent-instruction files (e.g. `CLAUDE.md`, `AGENTS.md`), compare their claims against what you
>    just verified in source. Flag any contradiction explicitly — stale docs that describe a
>    superseded design are a common and high-value finding. Ask whether to fix the stale doc as
>    part of this task.
> 3. **Ask before finalizing**, if genuinely unclear:
>    - Where should the doc live, and in what format? (Default recommendation: Markdown, checked
>      into the repo, e.g. `docs/DESIGN.md`.)
>    - How much diagram content? (Default recommendation: diagrams for key flows — an ERD and a
>      handful of sequence diagrams for the most important flows — mixed with narrative and
>      tables. Not text-only, not diagram-heavy.)
>    - Should discovered doc drift be fixed in the same task, or just flagged?
> 4. **Structure the document** using whichever of these sections apply to this system (skip what
>    doesn't exist, add what's missing — this list is a starting point, not a checklist to force):
>    1. Overview & Purpose — what the system is, who this doc is for, its scope.
>    2. Architecture at a Glance — stack, layering/module boundaries, deployment topology,
>       environment/config profiles.
>    3. Domain Model — every core entity with purpose and key relationships, plus a Mermaid ERD.
>    4. Interface Surface — every endpoint/command/method, grouped logically, with
>       auth/permissions and purpose.
>    5. Core Functional Flows — narrative *and* Mermaid sequence diagrams for the handful of flows
>       that matter most (typically: auth/session, the primary business transaction, and any
>       async/background flow).
>    6. Deep dive on the system's most distinctive subsystem(s) — full mechanics (what triggers
>       it, exact retry/failure/scheduling behavior), plus explicit "why this design, and what did
>       it replace or solve" reasoning. This is the section most likely to answer the kind of
>       question that prompted writing the doc in the first place.
>    7. Security Model.
>    8. Cross-cutting concerns: rate limiting / caching, error-handling contract (exception →
>       status → response shape), validation rules.
>    9. Scheduled/background jobs, if any.
>    10. Known Limitations / Tech Debt — real gaps you found while researching (missing
>        validation, hardcoded assumptions, single-instance assumptions, etc.), not hypothetical
>        ones.
> 5. **Every fact must be traceable to a file you actually read.** No invented field names, no
>    assumed status codes, no guessed trigger conditions. Where it adds clarity, cite file paths.
> 6. **Diagrams**: use Mermaid (renders natively in GitHub/most Markdown viewers) for the ERD and
>    sequence diagrams. Validate they're syntactically well-formed before finishing.
> 7. **Cross-link.** If an existing quick-reference doc (README/CLAUDE.md/AGENTS.md) exists, add a
>    one-line pointer to the new design doc from the relevant section, so readers can find the
>    deeper version without the two docs duplicating each other's content.
> 8. **Verify before calling it done**: re-check every endpoint, file path, and class/function name
>    mentioned against the current source tree; confirm diagrams parse; confirm the doc doesn't
>    contradict whatever existing docs you left in place.

---

## Notes on why this works

- The research-then-verify split matters: broad parallel exploration finds the shape of the
  system fast, but only re-reading the actual files before writing prevents confidently-stated
  wrong details (wrong field names, wrong status codes, describing a design that was already
  replaced).
- Explicitly hunting for doc/code drift is what turns a documentation task into something that
  also improves the codebase — stale docs are common and cheap to catch once you're already
  reading the source closely.
- Asking about format/diagram-depth/drift-handling before writing avoids producing 500 lines the
  user then has to redirect.
- The "deep dive on the distinctive subsystem" section is what separates a useful design doc from
  a restated file tree — it's usually the section that answers whatever question prompted someone
  to ask for the doc in the first place.
