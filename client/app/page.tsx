
const highlights = [
  {
    title: "Unified Memberships",
    description:
      "Automate sign-ups, renewals, locker rentals, and billing from one dashboard.",
  },
  {
    title: "Coach Intelligence",
    description:
      "AI-assisted rosters, readiness tracking, and instant feedback loops for coaches.",
  },
  {
    title: "Member Journeys",
    description:
      "Sell hybrid programs with personalized nudges that keep every athlete engaged.",
  },
];

const programs = [
  {
    name: "Strength Collective",
    focus: "Periodized lifting phases synced to recovery alerts",
    schedule: "Mon / Wed / Fri",
  },
  {
    name: "AlphaFlow",
    focus: "Mobility + metabolic circuits for hybrid athletes",
    schedule: "Daily staggered waves",
  },
  {
    name: "Pulse Ride Studio",
    focus: "Data-backed cycling sessions with live zones",
    schedule: "Thu / Sat + virtual",
  },
];

const stats = [
  { label: "Active members", value: "12.4K" },
  { label: "Studios powered", value: "48" },
  { label: "Avg. retention lift", value: "+32%" },
];

const accessOptions = [
  {
    title: "Gym portal",
    description:
      "Register a new facility or log in to manage studios, coaches, and revenue flows.",
    href: "/admin",
  },
  {
    title: "Coach portal",
    description:
      "Onboard as a coach, claim rosters, and access readiness data with one secure login.",
    href: "/coach",
  },
  {
    title: "Member login",
    description: "Check into classes, view recovery data, and stay on top of progress.",
    href: "/member",
  },
];

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-violet-50 via-white to-white text-slate-900 transition-colors dark:from-[#070012] dark:via-[#04000c] dark:to-black">
      <main className="relative mx-auto flex max-w-6xl flex-col gap-20 px-6 pb-24 pt-16 sm:px-10 lg:px-12">
        <div className="absolute inset-0 -z-10 overflow-hidden">
          <div
            className="absolute left-1/2 top-0 h-80 w-[38rem] -translate-x-1/2 rounded-full bg-violet-300/40 blur-[140px] dark:bg-violet-700/30"
            aria-hidden
          />
          <div
            className="absolute right-10 top-1/2 h-64 w-64 rounded-full bg-fuchsia-300/30 blur-[120px] dark:bg-fuchsia-700/30"
            aria-hidden
          />
        </div>

        <section className="grid items-center gap-14 rounded-[36px] border border-violet-200/60 bg-white/80 p-10 shadow-[0_25px_120px_rgba(99,57,255,0.12)] backdrop-blur-xl dark:border-violet-800/50 dark:bg-white/5">
          <div className="space-y-8 text-center sm:text-left">
            <span className="inline-flex items-center gap-2 rounded-full border border-violet-200/60 bg-violet-100/60 px-4 py-1 text-xs uppercase tracking-[0.2em] text-violet-700 dark:border-violet-700/40 dark:bg-violet-900/40 dark:text-violet-100">
              AlphaCore Platform
            </span>
            <div className="space-y-6">
              <h1 className="text-4xl font-semibold leading-tight text-[#19002e] sm:text-5xl lg:text-6xl dark:text-white">
                Gym management that feels handcrafted for high-performing teams.
              </h1>
              <p className="text-lg leading-relaxed text-slate-600 dark:text-slate-200">
                Orchestrate memberships, classes, and retail from a single control tower.
                AlphaCore tracks biofeedback, attendance, and revenue so you can focus on
                crafting world-class training experiences.
              </p>
            </div>
            <div className="grid gap-4 sm:grid-cols-3">
              {accessOptions.map((option) => (
                <a
                  key={option.title}
                  href={option.href}
                  className="rounded-2xl border border-violet-200/70 bg-gradient-to-br from-white to-violet-50/70 p-5 text-left shadow-[0_15px_45px_rgba(123,65,255,0.18)] transition hover:-translate-y-1 dark:border-violet-800/60 dark:from-white/10 dark:to-white/5"
                >
                  <p className="text-xs uppercase tracking-[0.3em] text-violet-500 dark:text-violet-200">
                    Access
                  </p>
                  <h3 className="mt-2 text-xl font-semibold text-[#22024a] dark:text-white">
                    {option.title}
                  </h3>
                  <p className="mt-2 text-sm text-slate-600 dark:text-slate-200">
                    {option.description}
                  </p>
                </a>
              ))}
            </div>
          </div>
        </section>

        <section className="grid gap-10">
          <div className="flex flex-col gap-6 sm:flex-row sm:items-end sm:justify-between">
            <div>
              <p className="text-sm font-semibold uppercase tracking-[0.3em] text-violet-600 dark:text-violet-300">
                Operating system
              </p>
              <h2 className="mt-3 text-3xl font-semibold text-[#1b0133] dark:text-white">
                Everything your gym needs to perform in sync.
              </h2>
            </div>
            <div className="flex gap-8 text-violet-700 dark:text-violet-200">
              {stats.map((stat) => (
                <div key={stat.label} className="text-center sm:text-left">
                  <p className="text-3xl font-semibold">{stat.value}</p>
                  <p className="text-sm uppercase tracking-[0.2em] text-violet-500 dark:text-violet-300">
                    {stat.label}
                  </p>
                </div>
              ))}
            </div>
          </div>
          <div className="grid gap-6 md:grid-cols-3">
            {highlights.map((feature) => (
              <article
                key={feature.title}
                className="rounded-3xl border border-violet-200/70 bg-white/70 p-6 shadow-[0_15px_55px_rgba(111,76,255,0.12)] transition hover:-translate-y-1 dark:border-violet-800/60 dark:bg-white/5"
              >
                <h3 className="text-xl font-semibold text-[#22024a] dark:text-white">
                  {feature.title}
                </h3>
                <p className="mt-3 text-base leading-relaxed text-slate-600 dark:text-slate-200">
                  {feature.description}
                </p>
              </article>
            ))}
          </div>
        </section>

        <section id="programs" className="space-y-8 rounded-[32px] bg-violet-50/80 p-8 shadow-inner shadow-violet-200/60 dark:bg-[#140827] dark:shadow-violet-900/60">
          <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
            <div>
              <p className="text-sm font-semibold uppercase tracking-[0.3em] text-violet-700 dark:text-violet-300">
                Signature programs
              </p>
              <h2 className="text-3xl font-semibold text-[#1d003b] dark:text-white">
                Built with adaptive scheduling.
              </h2>
            </div>
            <a
              href="#"
              className="inline-flex items-center rounded-full border border-violet-400/70 px-5 py-2 text-sm font-semibold text-violet-800 transition hover:translate-x-0.5 hover:border-violet-600 dark:border-violet-500/70 dark:text-violet-100"
            >
              Download brochure →
            </a>
          </div>
          <div className="grid gap-6 lg:grid-cols-3">
            {programs.map((program) => (
              <article
                key={program.name}
                className="rounded-3xl border border-violet-200/80 bg-white/80 p-6 transition hover:-translate-y-1 hover:border-violet-500 dark:border-violet-800/70 dark:bg-white/10"
              >
                <p className="text-xs uppercase tracking-[0.3em] text-violet-500 dark:text-violet-300">
                  {program.schedule}
                </p>
                <h3 className="mt-3 text-2xl font-semibold text-[#22024a] dark:text-white">
                  {program.name}
                </h3>
                <p className="mt-3 text-base text-slate-600 dark:text-slate-200">
                  {program.focus}
                </p>
              </article>
            ))}
          </div>
        </section>

        <section id="demo" className="rounded-[32px] border border-violet-200/70 bg-gradient-to-br from-violet-500 via-indigo-600 to-fuchsia-600 p-10 text-white shadow-[0_30px_120px_rgba(76,0,140,0.45)] dark:border-violet-700/50">
          <div className="flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">
            <div className="space-y-4">
              <p className="text-sm uppercase tracking-[0.4em] text-white/70">
                Early access
              </p>
              <h3 className="text-3xl font-semibold">
                Launch AlphaCore in your club in under 30 days.
              </h3>
              <p className="text-base text-white/80">
                White-glove onboarding, integrations with the tools you already run, and a
                fully themed member app in your own violet palette.
              </p>
            </div>
            <div className="flex flex-col gap-4 sm:flex-row">
              <input
                type="email"
                placeholder="team@yourstudio.com"
                className="h-12 min-w-[240px] rounded-full border border-white/60 bg-white/15 px-5 text-sm text-white placeholder:text-white/70 focus:border-white focus:outline-none"
              />
              <button className="h-12 rounded-full bg-white/90 px-6 text-sm font-semibold text-violet-800 transition hover:bg-white">
                Reserve onboarding
              </button>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}
