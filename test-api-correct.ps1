# Script de test pour l'API SGAOA

Write-Host "🚀 Test de connexion Backend/Frontend SGAOA" -ForegroundColor Green

# Test 1: Vérifier si le backend est démarré
Write-Host "`n📊 Test 1: Vérification du backend..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081" -Method GET -TimeoutSec 5
    Write-Host "✅ Backend accessible sur http://localhost:8081" -ForegroundColor Green
} catch {
    Write-Host "❌ Backend non accessible: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Test 2: Créer un utilisateur de test
Write-Host "`n👤 Test 2: Création utilisateur de test..." -ForegroundColor Yellow
$userData = @{
    nom = "Test"
    prenom = "User"
    email = "test$(Get-Random -Maximum 9999)@example.com"
    motDePasse = "password123"
    telephone = "0123456789"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/inscription" -Method POST -ContentType "application/json" -Body $userData
    Write-Host "✅ Utilisateur créé avec succès: $($response.prenom) $($response.nom)" -ForegroundColor Green
    Write-Host "   Email: $($response.email)" -ForegroundColor Cyan
    $testEmail = $response.email
} catch {
    Write-Host "❌ Erreur création utilisateur: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $errorBody = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($errorBody)
        $errorText = $reader.ReadToEnd()
        Write-Host "   Détail: $errorText" -ForegroundColor Red
    }
}

# Test 3: Test de connexion
Write-Host "`n🔐 Test 3: Test de connexion..." -ForegroundColor Yellow
$loginData = @{
    email = $testEmail
    motDePasse = "password123"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/connexion" -Method POST -ContentType "application/json" -Body $loginData
    Write-Host "✅ Connexion réussie!" -ForegroundColor Green
    Write-Host "   Token: $($response.accessToken.Substring(0, 50))..." -ForegroundColor Cyan
    Write-Host "   Utilisateur: $($response.utilisateur.prenom) $($response.utilisateur.nom)" -ForegroundColor Cyan
    $token = $response.accessToken
} catch {
    Write-Host "❌ Erreur connexion: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $errorBody = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($errorBody)
        $errorText = $reader.ReadToEnd()
        Write-Host "   Détail: $errorText" -ForegroundColor Red
    }
}

# Test 4: Test endpoint protégé
if ($token) {
    Write-Host "`n🛡️ Test 4: Test endpoint protégé..." -ForegroundColor Yellow
    $headers = @{
        "Authorization" = "Bearer $token"
        "Content-Type" = "application/json"
    }
    
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8081/api/comptes" -Method GET -Headers $headers
        Write-Host "✅ Endpoint protégé accessible!" -ForegroundColor Green
        Write-Host "   Nombre d'utilisateurs: $($response.Count)" -ForegroundColor Cyan
    } catch {
        Write-Host "❌ Erreur endpoint protégé: $($_.Exception.Message)" -ForegroundColor Red
        if ($_.Exception.Response) {
            $errorBody = $_.Exception.Response.GetResponseStream()
            $reader = New-Object System.IO.StreamReader($errorBody)
            $errorText = $reader.ReadToEnd()
            Write-Host "   Détail: $errorText" -ForegroundColor Red
        }
    }
}

Write-Host "`n🎉 Tests terminés!" -ForegroundColor Green
Write-Host "📱 Ouvrez test-frontend.html dans un navigateur pour une interface graphique" -ForegroundColor Cyan
