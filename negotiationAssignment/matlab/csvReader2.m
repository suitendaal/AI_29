%%
clear all; close all; clc;

%%
[Agents1, Data1, TotalS1, Util1] = csvToData2("tour (1).csv");
[Agents2, Data2, TotalS2, Util2] = csvToData2("tour (2).csv");
[Agents3, Data3, TotalS3, Util3] = csvToData2("tour (3).csv");
[Agents4, Data4, TotalS4, Util4] = csvToData2("tour (4).csv");
[Agents5, Data5, TotalS5, Util5] = csvToData2("tour (5).csv");
[Agents6, Data6, TotalS6, Util6] = csvToData2("tour (6).csv");
[Agents7, Data7, TotalS7, Util7] = csvToData2("tour (7).csv");
[Agents8, Data8, TotalS8, Util8] = csvToData2("tour (8).csv");
[Agents9, Data9, TotalS9, Util9] = csvToData2("tour (9).csv");
[Agents10, Data10, TotalS10, Util10] = csvToData2("tour (10).csv");

TotalX={TotalS1,TotalS2,TotalS3,TotalS4,TotalS5...
    ,TotalS6,TotalS7,TotalS8,TotalS9,TotalS10};
%%
for i=1:10
    disp(i);
    d=TotalX{i};
    d(:,3:end)=strrep(d(:,3:end),",",".");
    
    for j=1:length(d)

        if d(j,1)=="Group29_BoaParty"
            minU(j)=double(d(j,3));
            maxU(j)=double(d(j,4));
            nash(j)=double(d(j,5));
            pareto(j)=double(d(j,6));
            
        end
    end
    figure(i); hold on;
    subplot(1,3,1);
    plot(minU);hold on;
    subplot(1,3,1);
    plot(maxU);
    title("Min Max utility");
    
    subplot(1,3,2);
    plot(nash);
    title("Dist from Nash");
     
    subplot(1,3,3);
    plot(pareto);
    title("Dist from Pareto");
%     figure(i);hold on;title("Min Max utility");
%     plot(minU);plot(maxU);
%     figure(10+i);hold on;title("Dist from Nash");
%     plot(nash);
%     figure(20+i);hold on;title("Dist from Pareto");
%     plot(pareto);
end
%%
[Agents11, Data11, TotalS11, Util11] = csvToData2("tour11.csv");
H1=[];H2=[];

parfor k=1:length(Util11)
    d=TotalS11;
    d(:,3:end)=strrep(d(:,3:end),",",".");
    if d(k,1)=="Group29_BoaParty"
        H1=[H1 Util11(k,1)];       
    end
    if d(k,2)=="Group29_BoaParty"
        H2=[H2 Util11(k,2)];
    end
end
    figure(11);hold on; histogram(H1);
    figure(12);hold on; histogram(H2);
%%
[Agents11, Data11, Total11, Util11] = csvToData2("tour11.csv"); %party
csv2Histo(Total11, Util11)
[Agents12, Data12, Total12, Util12] = csvToData2("tour12.csv"); %jobs
csv2Histo(Total12, Util12);
[Agents13, Data13, Total13, Util13] = csvToData2("tour13.csv"); %laptop
csv2Histo(Total13, Util113);
%%
TC1=[];TB1=[];TH1=[];TC2=[];TB2=[];TH2=[];
for k=1:length(Util11)
    d=TotalS11;
    switch d(k,1)
        case "TimeDependentAgentConceder"
            if d(k,2)=="Group29_BoaParty"
                TC1=[TC1 Util11(k,2)];       
            end
        case "TimeDependentAgentBoulware"
            if d(k,2)=="Group29_BoaParty"
                TB1=[TB1 Util11(k,2)];       
            end
        case "TimeDependentAgentHardliner"
            if d(k,2)=="Group29_BoaParty"
                TH1=[TH1 Util11(k,2)];
            end
        otherwise 
%             disp("error")   
    end
    switch d(k,2)
        case "TimeDependentAgentConceder"
            if d(k,1)=="Group29_BoaParty"
                TC2=[TC2 Util11(k,1)];       
            end
        case "TimeDependentAgentBoulware"
            if d(k,1)=="Group29_BoaParty"
                TB2=[TB2 Util11(k,1)];       
            end
        case "TimeDependentAgentHardliner"
            if d(k,1)=="Group29_BoaParty"
                TH2=[TH2 Util11(k,1)]; 
            end
        otherwise 
%             disp("error")   
    end

end
figure; hold on;
subplot(2,3,1);xlim([0 1]); 
histogram(TC1,30);xlim([0 1]); title("Conceder vs Us");
subplot(2,3,2);
histogram(TB1,30);xlim([0 1]); title("Boulware vs Us");
subplot(2,3,3);
histogram(TH1,30);xlim([0 1]); title("Hardliner vs Us ");
subplot(2,3,4);
histogram(TC2,30);xlim([0 1]); title("Us vs Conceder ");
subplot(2,3,5);
histogram(TB2,30);xlim([0 1]); title("Us vs Boulware");
subplot(2,3,6);
histogram(TH2,30);xlim([0 1]); title("Us vs Hardliner");
%%
[Agents14, Data14, Total14, Util14] = csvToData2("tour14.csv"); %us party
csv2Histo(Total14, Util14);
[Agents15, Data15, Total15, Util15] = csvToData2("tour15.csv"); %us jobs
csv2Histo(Total15, Util15);
[Agents16, Data16, Total16, Util16] = csvToData2("tour16.csv"); %us laptops
csv2Histo(Total16, Util16);
%% only against agents
party2Histo(Total11, Util11,30,0,'Party domain');%party
party2Histo(Total12, Util12,10,1,'Jobs domain');%jobs
party2Histo(Total13, Util13,10,1,'Laptops domain');%laptops
%% with color
close all;
party2HistoColor(Total11, Util11,30,0,'Party domain',"tour14.csv");%party
party2HistoColor(Total12, Util12,10,1,'Jobs domain',"tour15.csv");%jobs
party2HistoColor(Total13, Util13,10,1,'Laptops domain',"tour16.csv");%laptops
%% complete domain plot
close all;
party2HistoDomain(Total11, Util11,30,0,'Party domain',"tour14.csv",8,1);%party
party2HistoDomain(Total12, Util12,10,1,'Jobs domain',"tour15.csv",8,1);%jobs
party2HistoDomain(Total13, Util13,10,1,'Laptops domain',"tour16.csv",8,1);%laptops
%% dist to Pareto dist to Nash
close all;clc;
figure;

t11=double(strrep(Total11(:,3:end),",","."));
t12=double(strrep(Total12(:,3:end),",","."));
t13=double(strrep(Total13(:,3:end),",","."));
x11=1:length(t11);
x12=1:length(t12);
x13=1:length(t13);
plot(x11,double(t11));hold on;
plot(x12,double(t12));
plot(x13,double(t13));
%%
clc; close all;
data2PaNa(Total11,Util11,10,'Party');
data2PaNa(Total12,Util12,10,'Jobs');
data2PaNa(Total13,Util13,10,'Laptops');
