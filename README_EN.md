<p align="center">
	<img alt="logo" width="140" src="./img/logo.png">
</p>

<h1 align="center">Crypto Exchange — Digital Asset Trading Solution</h1>

<p align="center">
  <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License">
  <img src="https://img.shields.io/badge/Vue-3.5-42b883.svg" alt="Vue 3">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-6db33f.svg" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Java-17-orange.svg" alt="Java 17">
  <img src="https://img.shields.io/badge/Spot%20Trading-orange.svg" alt="Spot">
  <img src="https://img.shields.io/badge/Futures%20Trading-yellow.svg" alt="Futures">
  <img src="https://img.shields.io/badge/PC%20Web-1677ff.svg" alt="PC Web">
</p>

<p align="center">
  <strong>Language:</strong> <a href="./README.md">中文</a> | English | <a href="./README_JA.md">日本語</a> | <a href="./README_KO.md">한국어</a>
</p>

<p align="center">
  A complete <strong>centralized digital asset exchange</strong> solution covering mobile, PC Web, admin console, and microservice backend.<br/>
  Supports spot, margin, USDT-margined / coin-margined futures, deposit & withdrawal, transfers, real-time quotes, and K-line charts. Try the live demo — contact us for source code and deployment.
</p>

<p align="center">
  <img alt="banner" src="./img/en_banner.png" width="100%">
</p>

---

## Live Demo

| Platform | URL | Description |
|----------|-----|-------------|
| **App H5** | [http://app.coin.vexcoin.top](http://app.coin.vexcoin.top) | Mobile browser experience |
| **Android App** | http://oss.coin.vexcoin.top/coin/app/bitcoin.apk | Android APK download |
| **PC Web** | [http://pc.coin.vexcoin.top](http://pc.coin.vexcoin.top) | Full desktop trading workspace |

| Demo Account | Password | Email Code |
|--------------|----------|------------|
| `111@gmail.com` | `111111` | `123456` |

> Demo environment is for feature preview only. Data may be reset periodically. Do not use for real assets.  
> <font color="red">**Demo available hours:** 20:00 - 23:00 (Beijing Time, UTC+8). Contact us to schedule a session at other times.</font>

---

## Features

- **Multi-platform** — Mobile App (H5 / iOS / Android), PC Web, admin console
- **Spot & Margin** — Limit / market / TP-SL orders, order book, K-line sync, borrow & repay
- **Futures** — USDT-margined & coin-margined, isolated / cross margin, leverage, funding rate, liquidation
- **Asset Management** — Deposit / withdrawal (multi-chain), transfers, full statement tracking
- **Real-time Market** — WebSocket price, order book, trades, K-line push
- **Account Security** — KYC, Google Auth, fund password, login protection
- **Operations** — Banners, announcements, message center, live support, referral
- **i18n** — Simplified Chinese / Traditional Chinese / English
- **Extensible** — Decoupled frontend & backend, microservice architecture, modular customization

---

## Project Modules

Modular architecture — each module can be deployed independently or combined:

| Module | Description | Stack |
|--------|-------------|-------|
| **binance_app** | Mobile client | uni-app + Vue 3 + Vite |
| **binance_pc** | PC Web client | Vue 3 + TypeScript + Element Plus |
| **binance_coin** | Backend microservices | Spring Boot 3 + Spring Cloud + Nacos |

Mobile and PC share the same backend API (`coin-app` microservice) with aligned features.

> This repository is a **showcase and overview entry point** with live demo links, screenshots, and architecture docs. Full source code is available via contact below.

---

## System Architecture

```mermaid
flowchart TB
    subgraph clients [Clients]
        APP[binance_app<br/>Mobile H5/iOS/Android]
        PC[binance_pc<br/>PC Web]
    end

    GW[Gateway]

    subgraph services [Microservices]
        APP_SVC[coin-app<br/>User API]
        EXCHANGE[coin-exchange<br/>Matching Engine]
        MARKET[coin-market<br/>Market Data]
        SYSTEM[coin-system<br/>System Mgmt]
        ADMIN_SVC[coin-admin<br/>Admin Service]
    end

    subgraph infra [Infrastructure]
        NACOS[Nacos<br/>Registry / Config]
        MYSQL[(MySQL)]
        REDIS[(Redis)]
        WS[WebSocket<br/>Real-time Push]
    end

    APP --> GW
    PC --> GW
    GW --> APP_SVC
    GW --> EXCHANGE
    GW --> MARKET
    GW --> SYSTEM
    GW --> ADMIN_SVC
    APP_SVC --> MYSQL
    APP_SVC --> REDIS
    EXCHANGE --> MYSQL
    EXCHANGE --> REDIS
    MARKET --> REDIS
    APP -.-> WS
    PC -.-> WS
    services --> NACOS
```

**Request flow:** Client → Gateway → Microservices → MySQL / Redis  
**Real-time data:** WebSocket channel for quotes, order book, and trades

---

## Tech Stack

| Layer | Technology | Notes |
|-------|------------|-------|
| Mobile | uni-app, Vue 3, Vite, Pinia, vk-uview-ui | H5 / iOS / Android |
| PC Web | Vue 3, TypeScript, Vite, Element Plus | Fixed 1280px+ desktop layout |
| Admin | Vue 3, Element Plus, Avue | Operations & business config |
| Backend | Spring Boot 3.2, Spring Cloud Alibaba | Java 17 |
| Microservices | Nacos, Gateway, OpenFeign | Service registry & routing |
| Storage | MySQL, Redis, MyBatis-Plus | Business data + cache |
| Real-time | WebSocket (MQTT wrapper) | Quotes / depth / K-line / trades |
| Charts | lightweight-charts | K-line display |
| Build | Maven (backend), Vite (frontend) | — |

---

## Screenshots

### Mobile App

<table align="center">
  <tr>
    <td align="center"><img src="./img/en1.png" width="180" alt="App Home" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/en2.png" width="180" alt="App Market" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/en3.png" width="180" alt="App Trade" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/en4.png" width="180" alt="App Futures" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
  <tr>
    <td align="center"><img src="./img/en5.png" width="180" alt="App Assets" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/en6.png" width="180" alt="App Deposit" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/en7.png" width="180" alt="App K-line" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/en8.png" width="180" alt="App Bills" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
</table>

### PC Web

<table align="center">
  <tr>
    <td align="center"><img src="./img/pc1.png" width="460" alt="PC Home" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/pc2.png" width="460" alt="PC Market" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
  <tr>
    <td align="center"><img src="./img/pc3.png" width="460" alt="PC Spot Trading" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/pc5.png" width="460" alt="PC Futures" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
  <tr>
    <td align="center"><img src="./img/pc6.png" width="460" alt="PC Wallet" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/pc7.png" width="460" alt="PC Bills" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
  <tr>
    <td align="center"><img src="./img/pc8.png" width="460" alt="PC Profile" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/pc9.png" width="460" alt="PC Deposit" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
</table>

### Admin Console

<table align="center">
  <tr>
    <td align="center"><img src="./img/web1.png" width="300" alt="Admin Dashboard" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/web2.png" width="300" alt="Admin Users" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/web3.png" width="300" alt="Admin Trading" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/web7.png" width="300" alt="Admin Assets" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
  <tr>
    <td align="center"><img src="./img/web4.png" width="300" alt="Admin Orders" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/web5.png" width="300" alt="Admin Risk" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/web6.png" width="300" alt="Admin Operations" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
    <td align="center"><img src="./img/web8.png" width="300" alt="Admin Settings" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" /></td>
  </tr>
</table>

---

## Directory Structure

<details open>
<summary><strong>binance_app — Mobile</strong></summary>

```
binance_app/
├── pages/              # Main tab pages (home, market, trade, futures, assets)
├── sub_package/        # Sub-packages (login, K-line, fund, bills, settings, 40+ pages)
├── components/         # Business components (custom-kline, custom-trade-order, etc.)
├── config/             # api.js, baseConfig.js
├── utils/              # request, websocket, coin formatting
└── locale/             # i18n (zh-Hans / zh-Hant / English)
```

</details>

<details open>
<summary><strong>binance_pc — PC Web</strong></summary>

```
binance_pc/
├── src/views/          # Pages (index, trade, contract, bills, settings)
├── src/components/     # Business components (custom-kline, custom-trade-depth, etc.)
├── src/router/         # Routes (routes-constants.ts)
├── src/config/         # api.ts, baseConfig.ts
└── src/utils/          # request, websocket, global modal controllers
```

</details>

<details open>
<summary><strong>binance_coin — Backend Microservices</strong></summary>

```
binance_coin/
├── coin-gateway/              # API Gateway
├── coin-service/
│   ├── coin-service-app/      # coin-app user business
│   ├── coin-service-exchange/ # coin-exchange matching engine
│   ├── coin-service-market/   # coin-market quotes
│   ├── coin-service-system/   # coin-system system management
│   └── coin-service-message/  # messaging
├── coin-common/               # Shared modules (starters, utilities)
└── coin-service-api/          # RPC interface definitions
```

</details>

---

## Commercial Support

For **full source licensing, custom development, and deployment**, contact us via:

<table align="center">
  <tr>
    <td align="center" valign="top">
      <a href="https://t.me/BITCOIN1688" target="_blank" style="display: inline-block; margin-bottom: 12px; padding: 8px 18px; font-size: 18px; font-weight: 600; color: #fff; text-decoration: none; background: #229ED9; border-radius: 8px;">Telegram Support</a><br/>
      <img alt="Telegram support" src="./img/tg.png" width="280" style="border: 1px solid #e5e7eb; border-radius: 10px; padding: 2px;" />
    </td>
  </tr>
</table>

---

## FAQ

### How to get the source code?
This repository is for showcase only and does not include full source code. For licensing, deployment, or custom development, please contact us via Telegram above.

### Do you support customization?
Yes. UI, trading flows, asset modules, and operations can be tailored to your requirements with a decoupled architecture.

### Does it include frontend and backend?
The solution covers mobile, PC Web, admin console, and microservice backend — deliverable in flexible combinations.

### Which platforms are supported?
Mobile: H5, iOS, Android; Desktop: PC Web; Admin: browser-based.

### Can you help with deployment?
Yes. We support staging and production deployment, domain setup, and basic integration. Contact us for details.

---

## Disclaimer

This project is a technical showcase and development base for digital asset exchange systems. **It does not constitute investment advice or any financial service commitment.**

- For learning, demonstration, and technical evaluation only — not for unauthorized financial operations
- Crypto and leveraged trading involve high risk; compliance and operational liability rests with the operator
- Provided "as is" without warranties of availability, stability, security, or profitability
- If user data is collected, the operator must comply with applicable laws and privacy regulations
- Inspired by mainstream exchange UX patterns — **not an official Binance product**, with no affiliation or authorization from Binance
