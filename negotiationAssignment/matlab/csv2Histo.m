function [] = csv2Histo(Total,Util)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
H1=[];H2=[];
parfor k=1:length(Util)
    d=Total;
    d(:,3:end)=strrep(d(:,3:end),",",".");
    if d(k,1)=="Group29_BoaParty"
        H1=[H1 Util(k,1)];       
    end
    if d(k,2)=="Group29_BoaParty"
        H2=[H2 Util(k,2)];
    end
end
    figure;hold on; histogram(H1,100);
    figure;hold on; histogram(H2,100);
end

