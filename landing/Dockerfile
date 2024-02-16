FROM node:18-alpine
WORKDIR /app
COPY . .
RUN npm install
WORKDIR /app
EXPOSE 3001
RUN npm run dev
