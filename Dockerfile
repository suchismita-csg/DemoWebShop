FROM jenkins/jenkins:lts

# Switch to root user to install dependencies
USER root

# Install dependencies
RUN apt-get update && apt-get install -y \
curl \
unzip \
wget \
libnss3 \
libgconf-2-4 \
libx11-xcb1 \
libxcb1 \
libxcb-dri3-0 \
libgbm1 \
libasound2 \
&& rm -rf /var/lib/apt/lists/*

# Install Google Chrome
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
&& dpkg -i google-chrome-stable_current_amd64.deb \
&& apt-get -fy install \
&& rm google-chrome-stable_current_amd64.deb

# Install ChromeDriver
RUN CHROME_VERSION=$(google-chrome --version | awk '{print $3}' | cut -d'.' -f1) && \
wget -q "https://chromedriver.storage.googleapis.com/$(curl -s https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_VERSION)/chromedriver_linux64.zip" -O chromedriver.zip && \
unzip chromedriver.zip && \
mv chromedriver /usr/local/bin/ && \
chmod +x /usr/local/bin/chromedriver && \
rm chromedriver.zip

# Install Selenium
RUN pip install --no-cache-dir selenium

# Switch back to Jenkins user
USER jenkins
