%% init
clear all; close all; clc;
addpath("csv"); disp("init");
%% Load data from CSV for each domain
warning('off', 'all');
[Agents11, Data11, Total11, Util11] = csvToData2("tour11.csv"); %party
% csv2Histo(Total11, Util11)
[Agents12, Data12, Total12, Util12] = csvToData2("tour12.csv"); %jobs
% csv2Histo(Total12, Util12);
[Agents13, Data13, Total13, Util13] = csvToData2("tour13.csv"); %laptop
% csv2Histo(Total13, Util13);
disp("loading done");
%% Csv Us vs US
% [Agents14, Data14, Total14, Util14] = csvToData2("tour14.csv"); %us party
% csv2Histo(Total14, Util14);
% [Agents15, Data15, Total15, Util15] = csvToData2("tour15.csv"); %us jobs
% csv2Histo(Total15, Util15);
% [Agents16, Data16, Total16, Util16] = csvToData2("tour16.csv"); %us laptops
% csv2Histo(Total16, Util16);
%% Only against agents
party2Histo(Total11, Util11,30,0,'Party domain');%party
party2Histo(Total12, Util12,10,1,'Jobs domain');%jobs
party2Histo(Total13, Util13,10,1,'Laptops domain');%laptops
disp("Only against agents");
%% Complete domain plot
party2HistoDomain(Total11, Util11,30,0,'Party domain',"tour14.csv",8,1);%party
party2HistoDomain(Total12, Util12,10,1,'Jobs domain',"tour15.csv",8,1);%jobs
party2HistoDomain(Total13, Util13,10,1,'Laptops domain',"tour16.csv",8,1);%laptops
disp("Domain plots");
%% Distance to Pareto dist to Nash
data2PaNa(Total11,Util11,10,'Party');
data2PaNa(Total12,Util12,10,'Jobs');
data2PaNa(Total13,Util13,10,'Laptops');
disp("Pareto & Nash");